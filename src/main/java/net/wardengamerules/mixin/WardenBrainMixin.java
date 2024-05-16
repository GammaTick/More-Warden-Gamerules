package net.wardengamerules.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WardenBrain.class})
public class WardenBrainMixin {
    public WardenBrainMixin() {}

    @Inject(method = {"resetDigCooldown"}, at = {@At("HEAD")}, cancellable = true)
    private static void resetDigCooldown(LivingEntity warden, CallbackInfo info) {
        if (warden.getBrain().hasMemoryModule(MemoryModuleType.DIG_COOLDOWN)) {
            GameRules.IntRule rule = warden.world.getGameRules().get(MoreWardenGamerules.WARDEN_DIG_COOLDOWN);
            warden.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, rule.get());
        }

        info.cancel();
    }

    @Inject(method = {"lookAtDisturbance"}, at = {@At("HEAD")}, cancellable = true)
    private static void lookAtDisturbance(WardenEntity warden, BlockPos pos, CallbackInfo info) {
        World world = warden.world;

        if (!warden.getWorld().getWorldBorder().contains(pos) || warden.getPrimeSuspect().isPresent() || warden.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isPresent()) {
            return;
        }
        WardenBrain.resetDigCooldown(warden);
        warden.getBrain().remember(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, world.getGameRules().getInt(MoreWardenGamerules.WARDEN_SNIFF_COOLDOWN));
        warden.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(pos), 100L);
        warden.getBrain().remember(MemoryModuleType.DISTURBANCE_LOCATION, pos, 100L);
        warden.getBrain().forget(MemoryModuleType.WALK_TARGET);

        info.cancel();
    }
}
