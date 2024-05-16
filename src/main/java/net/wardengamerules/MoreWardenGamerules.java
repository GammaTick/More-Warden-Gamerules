package net.wardengamerules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class MoreWardenGamerules {
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_USE_SONIC_BOOM;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_DISABLE_SHIELD;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_RIDE_ENTITIES;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_GIVE_DARKNESS;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_EMIT_VIBRATIONS;
    public static GameRules.Key<GameRules.IntRule> WARDEN_DIG_COOLDOWN;
    public static GameRules.Key<DoubleRule> SONIC_BOOM_DAMAGE;
    public static GameRules.Key<DoubleRule> SONIC_BOOM_KNOCKBACK_MULTIPLIER;
    public static GameRules.Key<DoubleRule> SONIC_BOOM_HORIZONTAL_RANGE;
    public static GameRules.Key<DoubleRule> SONIC_BOOM_VERTICAL_RANGE;
    public static GameRules.Key<GameRules.IntRule> SONIC_BOOM_COOLDOWN;
    public static GameRules.Key<GameRules.IntRule> WARDEN_SNIFF_COOLDOWN;
    public static GameRules.Key<GameRules.IntRule> WARDEN_DARKNESS_EFFECT_RANGE;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_IMMEDIATELY_DESPAWN;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_BE_LEASHED;

    public MoreWardenGamerules() {
    }

    public static void register() {
        CAN_WARDEN_USE_SONIC_BOOM = GameRuleRegistry.register("canWardenUseSonicBoom", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
        CAN_WARDEN_DISABLE_SHIELD = GameRuleRegistry.register("canWardenDisableShields", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
        CAN_WARDEN_RIDE_ENTITIES = GameRuleRegistry.register("canWardenRideEntities", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
        CAN_WARDEN_GIVE_DARKNESS = GameRuleRegistry.register("canWardenGiveDarkness", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
        CAN_WARDEN_EMIT_VIBRATIONS = GameRuleRegistry.register("canWardenEmitVibrations", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
        WARDEN_DIG_COOLDOWN = GameRuleRegistry.register("wardenDigCooldown", GameRules.Category.MOBS, GameRuleFactory.createIntRule(1200, 0));
        SONIC_BOOM_DAMAGE = GameRuleRegistry.register("sonicBoomDamage", GameRules.Category.MOBS, GameRuleFactory.createDoubleRule(10, 0));
        SONIC_BOOM_KNOCKBACK_MULTIPLIER = GameRuleRegistry.register("sonicBoomKnockbackMultiplier", GameRules.Category.MOBS, GameRuleFactory.createDoubleRule(1, 0));
        SONIC_BOOM_HORIZONTAL_RANGE = GameRuleRegistry.register("sonicBoomHorizontalRange", GameRules.Category.MOBS, GameRuleFactory.createDoubleRule(15, 0));
        SONIC_BOOM_VERTICAL_RANGE = GameRuleRegistry.register("sonicBoomVerticalRange", GameRules.Category.MOBS, GameRuleFactory.createDoubleRule(20, 0));
        SONIC_BOOM_COOLDOWN = GameRuleRegistry.register("sonicBoomCooldown", GameRules.Category.MOBS, GameRuleFactory.createIntRule(40, 0));
        WARDEN_SNIFF_COOLDOWN = GameRuleRegistry.register("wardenSniffCooldown", GameRules.Category.MOBS, GameRuleFactory.createIntRule(100, 0));
        WARDEN_DARKNESS_EFFECT_RANGE  = GameRuleRegistry.register("wardenDarknessEffectRange", GameRules.Category.MOBS, GameRuleFactory.createIntRule(20, 0));
        CAN_WARDEN_IMMEDIATELY_DESPAWN = GameRuleRegistry.register("canWardenImmediatelyDespawn", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
        CAN_WARDEN_BE_LEASHED = GameRuleRegistry.register("canWardenBeLeashed", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
    }
}
