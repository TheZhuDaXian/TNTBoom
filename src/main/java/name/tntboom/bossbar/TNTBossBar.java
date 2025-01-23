package name.tntboom.bossbar;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import name.tntboom.config.ModConfig;
import net.minecraft.entity.boss.BossBar.Color;
import net.minecraft.entity.boss.BossBar.Style;

public class TNTBossBar {
    private static final Identifier BOSS_BAR_ID = new Identifier("tntboom", "countdown");
    private static CommandBossBar bossBar;
    private static boolean isVisible = true;

    // 初始化Boss血条
    public static void init(MinecraftServer server) {
        bossBar = server.getBossBarManager().add(
            BOSS_BAR_ID,
            Text.literal("§c§l TNT倒计时")
        );
        bossBar.setColor(Color.RED);
        bossBar.setStyle(Style.PROGRESS);
        server.getPlayerManager().getPlayerList().forEach(bossBar::addPlayer);
    }

    // 显示/隐藏Boss血条
    public static void setVisible(boolean visible, ServerCommandSource source) {
        isVisible = visible;
        if (bossBar != null) {
            if (visible) {
                source.getServer().getPlayerManager().getPlayerList()
                    .forEach(player -> bossBar.addPlayer(player));
            } else {
                bossBar.clearPlayers();
            }
        }
    }

    // 更新Boss血条进度和显示
    public static void update(MinecraftServer server, int tickCounter) {
        if (bossBar == null || !isVisible) return;
        
        float progress = 1.0f - ((float)tickCounter / (ModConfig.getIntervalSeconds() * 20));
        int secondsLeft = ModConfig.getIntervalSeconds() - (tickCounter / 20);
        
        bossBar.setPercent(progress);
        bossBar.setName(Text.literal(String.format("§c§l TNT倒计时 §e%d §c秒", secondsLeft)));
        
        // 确保所有玩家都能看到boss条
        server.getPlayerManager().getPlayerList().forEach(player -> {
            if (!bossBar.getPlayers().contains(player)) {
                bossBar.addPlayer(player);
            }
        });
    }
}
