package me.ansandr.superfly.settings;

import me.ansandr.superfly.SuperFly;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Settings {

    private SuperFly plugin;
    private FileConfiguration config;

    public static boolean PARTICLE_ENABLED;
    public static boolean PARTICLE_SOUND;
    public static String PARTICLE_LOCATION;

    public static boolean FLY_EXP_ENABLED;
    public static int FLY_EXP_PERIOD;
    public static long FLY_EXP_COST;

    public static boolean FLY_MONEY_ENABLED;
    public static int FLY_MONEY_PERIOD;
    public static double FLY_MONEY_COST;

    public static boolean FLY_TEMP_ENABLE;
    public static int FLY_TEMP_DEFAULT;
    public static List<Integer> FLY_TEMP_OPTIONS;

    public static boolean FLY_RADIUS_ENABLE;
    public static int FLY_RADIUS_PERIOD;
    public static Radius FLY_RADIUS_CHECKTYPE;
    public static List<Integer> FLY_RADIUS_OPTIONS;

    public static boolean REST_PVP;
    public static boolean REST_PVE;
    public static boolean REST_PVE_BOSS;

    public static boolean REST_PROJECTILE;
    public static boolean REST_INTERACT;

    public static boolean TIMER_CHAT;
    public static boolean TIMER_ACTIONBAR;
    public static boolean TIMER_BOSSBAR;

    public static boolean ENABLE_WORLD_PERMISSION;

    private final Map<Option, Object> options = new EnumMap<Option, Object>(Option.class);

    public Settings(SuperFly plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        load();
    }

    public void load() {
        PARTICLE_ENABLED = config.getBoolean("particle.enabled");
        PARTICLE_SOUND = config.getBoolean("particle.play_sound");
        PARTICLE_LOCATION = config.getString("particle.coordinates");

        FLY_EXP_ENABLED = config.getBoolean("expFly.enabled");
        FLY_EXP_PERIOD = config.getInt("expFly.period");
        FLY_EXP_COST = config.getLong("expFly.cost");

        FLY_MONEY_ENABLED = config.getBoolean("moneyFly.enabled");
        FLY_MONEY_PERIOD = config.getInt("moneyFly.period");
        FLY_MONEY_COST = config.getDouble("moneyFly.cost");

        FLY_TEMP_ENABLE = config.getBoolean("tempFly.cost");
        FLY_TEMP_DEFAULT = config.getInt("tempFly.default_time");
        FLY_TEMP_OPTIONS = config.getIntegerList("tempFly.perm_options");

        FLY_RADIUS_ENABLE = config.getBoolean("radiusFly.enable");
        FLY_RADIUS_PERIOD = config.getInt("radiusFly.period");
        FLY_RADIUS_CHECKTYPE = getRadius(config.getString("radiusFly.inside_check"));
        FLY_RADIUS_OPTIONS = config.getIntegerList("radiusFly.perm_options");

        REST_PVP = config.getBoolean("restrictions.pvp");
        REST_PVE = config.getBoolean("restrictions.pve");
        REST_PROJECTILE = config.getBoolean("restrictions.projectile");
        REST_INTERACT = config.getBoolean("restrictions.interact");

        TIMER_CHAT = config.getBoolean("timers.chat");
        TIMER_ACTIONBAR = config.getBoolean("timers.actionBar");
        TIMER_BOSSBAR = config.getBoolean("timers.bossBar");

        ENABLE_WORLD_PERMISSION = config.getBoolean("enable_world_permission");

    }

    public Radius getRadius(String name) {
        switch (name) {
            case "sphere":
                return Radius.SPHERE;
            case "cube":
            default:
                return Radius.CUBE;
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public enum Radius {
        CUBE("cube"), SPHERE("sphere");

        private final String name;

        Radius(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
