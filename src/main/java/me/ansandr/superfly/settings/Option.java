package me.ansandr.superfly.settings;

public enum Option {
    PARTICLE_ENABLED("particle.enabled"),
    PARTICLE_SOUND("particle.play_sound"),
    PARTICLE_LOCATION("particle,coordinates"),
    FLY_EXP_ENABLED("expFly.enabled"),
    FLY_EXP_PERIOD("expFly.period"),
    FLY_EXP_COST("expFly.cost"),
    FLY_MONEY_ENABLED("moneyFly.enabled"),
    FLY_MONEY_PERIOD("moneyFly.period"),
    FLY_MONEY_COST("moneyFly.cost"),
    FLY_TEMP_ENABLE("tempFly.enable"),
    FLY_TEMP_DEFAULT("tempFly.defaultTime"),
    FLY_TEMP_OPTIONS("tempFly.perm_options"),
    FLY_RADIUS_ENABLE("radiusFly.enable"),
    FLY_RADIUS_CHECKTYPE("radiusFly.inside_check"),
    FLY_OPTIONS("radiusFly.perm_options"),
    REST_DAMAGE("restrictions.damage"),
    REST_PROJECTILE("restrictions.projectile"),
    REST_INTERACT("restrictions.interact"),
    ENABLE_WORLD_PERMISSION("enable_world_permission");

    private final String path;

    Option(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}