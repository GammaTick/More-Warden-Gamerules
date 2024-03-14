package net.wardengamerules.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.world.World;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({WardenEntity.class})
public abstract class CanWardenEmitVibrations extends HostileEntity {

    protected CanWardenEmitVibrations(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = {"occludeVibrationSignals"},
            at = {@At("TAIL")},
            cancellable = true
    )
    protected boolean occludeVibrationSignals(CallbackInfoReturnable<Boolean> info) {
        return !this.world.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_EMIT_VIBRATIONS);
    }
}