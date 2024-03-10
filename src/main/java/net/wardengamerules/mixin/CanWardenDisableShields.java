package net.wardengamerules.mixin;

import net.minecraft.entity.mob.WardenEntity;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({WardenEntity.class})
public abstract class CanWardenDisableShields {
    public CanWardenDisableShields() {
    }

    @Inject(
            method = {"disablesShield"},
            at = {@At("TAIL")},
            cancellable = true
    )
    private boolean disablesShield(CallbackInfoReturnable<Boolean> info) {
        return ((WardenEntity) (Object) this).getWorld().getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_DISABLE_SHIELD);
    }
}