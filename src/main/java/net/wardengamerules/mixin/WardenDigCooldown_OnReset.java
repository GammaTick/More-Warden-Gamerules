package net.wardengamerules.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.util.Unit;
import net.minecraft.world.GameRules;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WardenBrain.class})
public abstract class WardenDigCooldown_OnReset {

    @Inject(
            method = {"resetDigCooldown"},
            at = {@At("TAIL")},
            cancellable = true
    )
    private static void resetDigCooldown(LivingEntity warden, CallbackInfo info) {
        if (warden.getBrain().hasMemoryModule(MemoryModuleType.DIG_COOLDOWN)) {
            GameRules.IntRule rule = warden.world.getGameRules().get(MoreWardenGamerules.WARDEN_DIG_COOLDOWN);

            if (rule.get() < 0) {
                rule.set(1200, warden.world.getServer());
            }
            warden.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, rule.get());
        }
    }
}
