package name.tntboom.config;

public class ModConfig {
    // 默认10秒生成一次TNT
    private static int intervalSeconds = 10;
    // 默认3秒后爆炸 (60 ticks)
    private static int fuseTicks = 60;
    
    public static int getIntervalSeconds() {
        return intervalSeconds;
    }
    
    public static void setIntervalSeconds(int seconds) {
        intervalSeconds = seconds;
    }
    
    public static int getFuseTicks() {
        return fuseTicks;
    }
    
    public static void setFuseTicks(int ticks) {
        fuseTicks = ticks;
    }
}
