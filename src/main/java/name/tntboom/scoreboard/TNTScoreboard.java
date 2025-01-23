package name.tntboom.scoreboard;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import name.tntboom.config.ModConfig;

public class TNTScoreboard {
    private static final String OBJECTIVE_NAME = "TNTCountdown";
    
    public static void initScoreboard(MinecraftServer server) {
        Scoreboard scoreboard = server.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjective(OBJECTIVE_NAME);
        if (objective == null) {
            objective = scoreboard.addObjective(OBJECTIVE_NAME, 
                ScoreboardCriterion.DUMMY, 
                Text.literal("§c§l TNT倒计时 "), 
                ScoreboardCriterion.RenderType.INTEGER);
        }
        scoreboard.setObjectiveSlot(1, objective);
        clearScores(server);
    }
    
    public static void updateScoreboard(MinecraftServer server, int tickCounter) {
        int secondsLeft = ModConfig.getIntervalSeconds() - (tickCounter / 20);
        server.getPlayerManager().getPlayerList().forEach(player -> {
            Scoreboard scoreboard = player.getScoreboard();
            ScoreboardObjective objective = scoreboard.getObjective(OBJECTIVE_NAME);
            if (objective != null) {
                scoreboard.getPlayerScore("§e还剩下", objective).setScore(secondsLeft);
                scoreboard.getPlayerScore("§c秒", objective).setScore(secondsLeft);
            }
        });
    }
    
    private static void clearScores(MinecraftServer server) {
        Scoreboard scoreboard = server.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjective(OBJECTIVE_NAME);
        if (objective != null) {
            server.getPlayerManager().getPlayerList().forEach(player -> 
                scoreboard.resetPlayerScore(player.getEntityName(), objective));
        }
    }
}
