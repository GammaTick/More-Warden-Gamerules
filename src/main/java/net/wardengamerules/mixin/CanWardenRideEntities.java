package net.wardengamerules.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin({WardenEntity.class})
public class CanWardenRideEntities {
    public CanWardenRideEntities() {
    }

    @Inject(
            method = {"canStartRiding"},
            at = {@At("TAIL")},
            cancellable = true
    )
    private boolean canStartRiding(Entity entity, CallbackInfoReturnable<Boolean> infoReturnable) {
        return entity.world.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_RIDE_ENTITIES);
    }
}
