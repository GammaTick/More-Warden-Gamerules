package net.wardengamerules.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.wardengamerules.MoreWardenGamerules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SonicBoomTask.class})
public class SonicBoomMixin {
    private static final int SOUND_DELAY = MathHelper.ceil(34.0);
    private static final int RUN_TIME = MathHelper.ceil(60.0F);


    @Inject(method = {"keepRunning"}, at = {@At("HEAD")}, cancellable = true)
    private void keepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l, CallbackInfo info) {
        wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> wardenEntity.getLookControl().lookAt(target.getPos()));
        if (wardenEntity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) || wardenEntity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
            return;
        }
        wardenEntity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - SOUND_DELAY);
        wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(wardenEntity::isValidTarget).filter(target -> wardenEntity.isInRange((Entity)target,
                serverWorld.getGameRules().get(MoreWardenGamerules.SONIC_BOOM_HORIZONTAL_RANGE).get(),
                serverWorld.getGameRules().get(MoreWardenGamerules.SONIC_BOOM_VERTICAL_RANGE).get())).ifPresent(target -> {
            Vec3d vec3d = wardenEntity.getPos().add(0.0, 1.6f, 0.0);
            Vec3d vec3d2 = target.getEyePos().subtract(vec3d);
            Vec3d vec3d3 = vec3d2.normalize();
            for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; ++i) {
                Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
                serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
            }
            wardenEntity.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0f, 1.0f);
            target.damage(DamageSource.sonicBoom(wardenEntity), (float) serverWorld.getGameRules().get(MoreWardenGamerules.SONIC_BOOM_DAMAGE).get());
            double d = 0.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
            double e = 2.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
            double knockbackMultiplier = serverWorld.getGameRules().get(MoreWardenGamerules.SONIC_BOOM_KNOCKBACK_MULTIPLIER).get();
            target.addVelocity(vec3d3.getX() * e * knockbackMultiplier, vec3d3.getY() * d * knockbackMultiplier,
                    vec3d3.getZ() * e * knockbackMultiplier);
        });

        info.cancel();
    }

    @Inject(method = {"finishRunning"}, at = {@At("HEAD")}, cancellable = true)
    private void finishRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l, CallbackInfo info) {
        SonicBoomTask.cooldown(wardenEntity, serverWorld.getGameRules().getInt(MoreWardenGamerules.SONIC_BOOM_COOLDOWN));
        info.cancel();
    }

    @Inject(method = {"shouldRun"}, at = {@At("RETURN")}, cancellable = true)
    private void shouldRun(ServerWorld serverWorld, WardenEntity wardenEntity, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(wardenEntity.isInRange(wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get(),
                serverWorld.getGameRules().get(MoreWardenGamerules.SONIC_BOOM_HORIZONTAL_RANGE).get(),
                serverWorld.getGameRules().get(MoreWardenGamerules.SONIC_BOOM_VERTICAL_RANGE).get()) &&
                serverWorld.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_USE_SONIC_BOOM));
    }
}
