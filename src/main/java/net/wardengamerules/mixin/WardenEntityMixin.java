package net.wardengamerules.mixin;


import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.wardengamerules.MoreWardenGamerules;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({WardenEntity.class})
public class WardenEntityMixin extends HostileEntity {
    protected WardenEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = {"disablesShield"}, at = {@At("RETURN")}, cancellable = true)
    private void disablesShield(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(this.world.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_DISABLE_SHIELD));
    }

    @Inject(method = {"occludeVibrationSignals"}, at = {@At("RETURN")}, cancellable = true)
    private void occludeVibrationSignals(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(!this.world.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_EMIT_VIBRATIONS));
    }

    @Inject(method = {"addDarknessToClosePlayers"}, at = {@At("HEAD")}, cancellable = true)
    private static void addDarknessToClosePlayers(ServerWorld world, Vec3d pos, @Nullable Entity entity, int range, CallbackInfo info) {
        if (world.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_GIVE_DARKNESS)) {
            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.DARKNESS, 260, 0, false, false);
            StatusEffectUtil.addEffectToPlayersWithinDistance(world, entity, pos, world.getGameRules().getInt(MoreWardenGamerules.WARDEN_DARKNESS_EFFECT_RANGE), statusEffectInstance, 200);
        }

        info.cancel();
    }

    @Inject(method = {"canStartRiding"}, at = {@At("RETURN")}, cancellable = true)
    private void canStartRiding(Entity entity, CallbackInfoReturnable<Boolean> info) {
        boolean bl = this.world.getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_RIDE_ENTITIES);
        info.setReturnValue(bl);
    }

    @Inject(method = "initialize", at = @At("HEAD"), cancellable = true)
    private void initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt, CallbackInfoReturnable<EntityData> info) {
        int duration = this.world.getGameRules().getInt(MoreWardenGamerules.WARDEN_DIG_COOLDOWN);

        this.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, duration);
        if (spawnReason == SpawnReason.TRIGGERED) {
            this.setPose(EntityPose.EMERGING);
            this.getBrain().remember(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
            this.playSound(SoundEvents.ENTITY_WARDEN_AGITATED, 5.0f, 1.0f);
        }
        info.setReturnValue(super.initialize(world, difficulty, spawnReason, entityData, entityNbt));
        info.cancel();
    }

    @Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
    private void tryAttack(Entity target, CallbackInfoReturnable<Boolean> info) {
        this.world.sendEntityStatus(this, (byte)4);
        this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 10.0F, this.getSoundPitch());
        SonicBoomTask.cooldown(this, this.getWorld().getGameRules().getInt(MoreWardenGamerules.SONIC_BOOM_COOLDOWN));
        info.setReturnValue(super.tryAttack(target));
        info.cancel();
    }

    @Inject(method = "updateAttackTarget", at = @At("HEAD"), cancellable = true)
    private void updateAttackTarget(LivingEntity target, CallbackInfo info) {
        this.getBrain().forget(MemoryModuleType.ROAR_TARGET);
        UpdateAttackTargetTask.updateAttackTarget(this, target);

        int cooldown = this.getWorld().getGameRules().getInt(MoreWardenGamerules.SONIC_BOOM_COOLDOWN);

        if (cooldown == 40) {
            SonicBoomTask.cooldown(this, 200);
        } else {
            SonicBoomTask.cooldown(this, this.getWorld().getGameRules().getInt(MoreWardenGamerules.SONIC_BOOM_COOLDOWN) * 5);
        }

        info.cancel();
    }

    @Inject(method = "canImmediatelyDespawn", at = @At("TAIL"), cancellable = true)
    public void canImmediatelyDespawn(double distanceSquared, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(this.getWorld().getGameRules().getBoolean(MoreWardenGamerules.CAN_WARDEN_IMMEDIATELY_DESPAWN));
    }
}