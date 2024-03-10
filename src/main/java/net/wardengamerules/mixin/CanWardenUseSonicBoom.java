package net.wardengamerules.mixin;

import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SonicBoomTask.class})
public class CanWardenUseSonicBoom {
    public CanWardenUseSonicBoom() {
    }

    @Inject(
            method = {"shouldRun"},
            at = {@At("TAIL")},
            cancellable = true
    )
    private boolean shouldRun(ServerWorld serverWorld, WardenEntity wardenEntity, CallbackInfoReturnable<Boolean> info) {
        return wardenEntity.getWorld().getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_USE_SONIC_BOOM);
    }
}