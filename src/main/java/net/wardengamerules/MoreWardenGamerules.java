package net.wardengamerules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class MoreWardenGamerules {
    public static GameRules.Key<GameRules.BooleanRule> CAN_WARDEN_USE_SONIC_BOOM;

    public MoreWardenGamerules() {
    }

    public static void register() {
        CAN_WARDEN_USE_SONIC_BOOM = GameRuleRegistry.register("canWardenUseSonicBoom", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    }
}
