package net.wardengamerules.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MobEntity.class})
public class MobEntityMixin {

    @Inject(method = {"canBeLeashedBy"}, at = {@At("TAIL")}, cancellable = true)
    private boolean canBeLeashedBy(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        MobEntity entity = (MobEntity) (Object) this;

        boolean bl = false;

        if (entity.getType().equals(EntityType.WARDEN)) {
            bl = true;
        } else if (!entity.isLeashed() && !(this instanceof Monster)) {
            bl = true;
        }

        info.setReturnValue(bl);
        return bl;
    }
}
