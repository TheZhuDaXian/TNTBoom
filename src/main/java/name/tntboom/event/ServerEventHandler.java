package name.tntboom.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;
import name.tntboom.config.ModConfig;
import name.tntboom.bossbar.TNTBossBar;

public class ServerEventHandler {
    private int tickCounter = 0;
    
    public void onServerTick(MinecraftServer server) {
        tickCounter++;
        if (tickCounter >= ModConfig.getIntervalSeconds() * 20) {
            tickCounter = 0;
            spawnTNT(server);
        }
        TNTBossBar.update(server, tickCounter);
    }
    
    private void spawnTNT(MinecraftServer server) {
        server.getWorlds().forEach(world -> 
            world.getPlayers().forEach(player -> {
                BlockPos pos = player.getBlockPos();
                TntEntity tntEntity = new TntEntity(world, 
                    pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, null);
                tntEntity.setFuse(ModConfig.getFuseTicks());
                world.spawnEntity(tntEntity);
            }));
    }
}
