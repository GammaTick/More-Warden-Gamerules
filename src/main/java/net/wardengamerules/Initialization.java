package net.wardengamerules;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Initialization implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("more-warden-gamerules");

	@Override
	public void onInitialize() {
		MoreWardenGamerules.register();
		LOGGER.info("More Warden Gamerules were added to your game successfully!");
	}
}