package net.wardengamerules.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.wardengamerules.MoreWardenGamerules;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WardenEntity.class})
public class CanWardenGiveDarkness {
    public CanWardenGiveDarkness() {
    }

    @Inject(
            method = {"addDarknessToClosePlayers"},
            at = {@At("INVOKE")},
            cancellable = true
    )
    private static void addDarknessToClosePlayers(ServerWorld world, Vec3d pos, @Nullable Entity entity, int range, CallbackInfo info) {
        if (!world.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_GIVE_DARKNESS)) {
            info.cancel();
        }
    }
}