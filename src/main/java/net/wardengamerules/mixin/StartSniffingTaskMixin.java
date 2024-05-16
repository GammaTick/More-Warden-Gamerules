package net.wardengamerules.mixin;


import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.StartSniffingTask;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({StartSniffingTask.class})
public class StartSniffingTaskMixin {
    private static final IntProvider COOLDOWN = UniformIntProvider.create(100, 200);

    @Inject(method = {"run"}, at = {@At("HEAD")}, cancellable = true)
    private void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l, CallbackInfo info) {
        int cooldown = serverWorld.getGameRules().get(MoreWardenGamerules.WARDEN_SNIFF_COOLDOWN).get();

        Brain<WardenEntity> brain = wardenEntity.getBrain();
        brain.remember(MemoryModuleType.IS_SNIFFING, Unit.INSTANCE);

        if (cooldown == -1) {
            brain.remember(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, COOLDOWN.get(serverWorld.getRandom()));
        } else {
            brain.remember(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, cooldown);
        }

        brain.forget(MemoryModuleType.WALK_TARGET);
        wardenEntity.setPose(EntityPose.SNIFFING);
        info.cancel();
    }
}
