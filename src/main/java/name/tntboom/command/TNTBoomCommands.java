package name.tntboom.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.command.CommandRegistryAccess;
import name.tntboom.config.ModConfig;
import name.tntboom.bossbar.TNTBossBar;

public class TNTBoomCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, 
                              CommandRegistryAccess registryAccess, 
                              CommandManager.RegistrationEnvironment environment) {
        // 注册主命令及子命令
        dispatcher.register(CommandManager.literal("tntboom")
            // TNT生成间隔设置
            .then(CommandManager.literal("interval")
                .then(CommandManager.argument("seconds", IntegerArgumentType.integer(1))
                    .executes(TNTBoomCommands::setInterval)))
            // TNT引信时间设置
            .then(CommandManager.literal("fusetime")
                .then(CommandManager.argument("ticks", IntegerArgumentType.integer(1))
                    .executes(TNTBoomCommands::setFuseTime)))
            // Boss血条控制
            .then(CommandManager.literal("bossbar")
                .then(CommandManager.literal("show")
                    .executes(TNTBoomCommands::showBossBar))
                .then(CommandManager.literal("hide")
                    .executes(TNTBoomCommands::hideBossBar))));
    }

    // 命令执行方法
    private static int setInterval(CommandContext<ServerCommandSource> context) {
        int seconds = IntegerArgumentType.getInteger(context, "seconds");
        ModConfig.setIntervalSeconds(seconds);
        context.getSource().sendFeedback(() -> 
            Text.literal("§6[TNT爆炸] §a生成间隔已设置为 §e" + seconds + " §a秒"), true);
        return 1;
    }

    private static int setFuseTime(CommandContext<ServerCommandSource> context) {
        int ticks = IntegerArgumentType.getInteger(context, "ticks");
        ModConfig.setFuseTicks(ticks);
        context.getSource().sendFeedback(() -> 
            Text.literal("§6[TNT爆炸] §a引信时间已设置为 §e" + ticks + " ticks §7(§e" + 
            String.format("%.1f", ticks/20.0) + " §a秒§7)"), true);
        return 1;
    }

    private static int showBossBar(CommandContext<ServerCommandSource> context) {
        TNTBossBar.setVisible(true, context.getSource());
        context.getSource().sendFeedback(() -> 
            Text.literal("§6[TNT爆炸] §a已显示Boss血条"), true);
        return 1;
    }

    private static int hideBossBar(CommandContext<ServerCommandSource> context) {
        TNTBossBar.setVisible(false, context.getSource());
        context.getSource().sendFeedback(() -> 
            Text.literal("§6[TNT爆炸] §a已隐藏Boss血条"), true);
        return 1;
    }
}
