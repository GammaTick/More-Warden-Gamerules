package net.wardengamerules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class MoreWardenGamerules {
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_USE_SONIC_BOOM;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_DISABLE_SHIELD;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_RIDE_ENTITIES;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_GIVE_DARKNESS;
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_EMIT_VIBRATIONS;
    public static GameRules.Key<GameRules.IntRule> WARDEN_DIG_COOLDOWN;

    public MoreWardenGamerules() {
    }

    public static void register() {
        CAN_WARDEN_USE_SONIC_BOOM = GameRuleRegistry.register("canWardenUseSonicBoom", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
        CAN_WARDEN_DISABLE_SHIELD = GameRuleRegistry.register("canWardenDisableShields", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
        CAN_WARDEN_RIDE_ENTITIES = GameRuleRegistry.register("canWardenRideEntities", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
        CAN_WARDEN_GIVE_DARKNESS = GameRuleRegistry.register("canWardenGiveDarkness", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
        CAN_WARDEN_EMIT_VIBRATIONS = GameRuleRegistry.register("canWardenEmitVibrations", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
        WARDEN_DIG_COOLDOWN = GameRuleRegistry.register("wardenDigCooldown", GameRules.Category.MOBS, GameRuleFactory.createIntRule(1200));
    }
}
