package name.tntboom.config;

public class ModConfig {
    private static int intervalSeconds = 10;
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
