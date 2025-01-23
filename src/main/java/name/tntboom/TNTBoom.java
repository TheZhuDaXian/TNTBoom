package name.tntboom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback; // 更改为 v2
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import name.tntboom.command.TNTBoomCommands;
import name.tntboom.event.ServerEventHandler;
import name.tntboom.scoreboard.TNTScoreboard;
import name.tntboom.bossbar.TNTBossBar;

public class TNTBoom implements ModInitializer {
    public static final String MOD_ID = "tntboom";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private final ServerEventHandler eventHandler = new ServerEventHandler();

    @Override
    public void onInitialize() {
        LOGGER.info("TNT爆炸模组已启动!");
        
        CommandRegistrationCallback.EVENT.register(TNTBoomCommands::register);
        ServerTickEvents.END_SERVER_TICK.register(eventHandler::onServerTick);
        ServerLifecycleEvents.SERVER_STARTED.register(TNTBossBar::init);
    }
}