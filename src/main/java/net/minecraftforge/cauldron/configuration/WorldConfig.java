package net.minecraftforge.cauldron.configuration;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class WorldConfig
{
    private final String worldName;
    public ConfigBase baseConfig;
    private boolean verbose;
    private final String wsd_text = "world-settings.default.";
    private final String ws_text = "world-settings.";

    public WorldConfig(String worldName, ConfigBase configFile)
    {
        this.worldName = worldName.toLowerCase();
        this.baseConfig = configFile;
        if (worldName.toLowerCase().contains("dummy")) return;
    }

    public void save()
    {
        baseConfig.save();
    }

    private void log(String s)
    {
        if ( verbose )
        {
            MinecraftServer.getServer().logInfo( s );
        }
    }

    public void set(String path, Object val)
    {
        baseConfig.config.set( path, val );
    }

    public boolean isBoolean(String path)
    {
        return baseConfig.config.isBoolean(path);
    }

    public boolean getBoolean(String path, boolean def)
    {
        if (baseConfig.settings.get(wsd_text + path) == null)
        {
            baseConfig.settings.put(wsd_text + path, new BoolSetting(baseConfig, wsd_text + path, def, ""));
        }

        baseConfig.config.addDefault( wsd_text + path, def );
        return baseConfig.config.getBoolean( ws_text + worldName + "." + path, baseConfig.config.getBoolean( wsd_text + path ) );
    }

    private double getDouble(String path, double def)
    {
        baseConfig.config.addDefault( wsd_text + path, def );
        return baseConfig.config.getDouble( ws_text + worldName + "." + path, baseConfig.config.getDouble( wsd_text + path ) );
    }

    public int getInt(String path, int def)
    {
        if (baseConfig.settings.get(wsd_text + path) == null)
        {
            baseConfig.settings.put(wsd_text + path, new IntSetting(baseConfig, wsd_text + path, def, ""));
        }

        baseConfig.config.addDefault( wsd_text + path, def );
        return baseConfig.config.getInt( ws_text + worldName + "." + path, baseConfig.config.getInt( wsd_text + path ) );
    }

    private <T> List getList(String path, T def)
    {
        baseConfig.config.addDefault( wsd_text + path, def );
        return (List<T>) baseConfig.config.getList( ws_text + worldName + "." + path, baseConfig.config.getList( wsd_text + path ) );
    }

    private String getString(String path, String def)
    {
        baseConfig.config.addDefault( wsd_text + path, def );
        return baseConfig.config.getString( ws_text + worldName + "." + path, baseConfig.config.getString( wsd_text + path ) );
    }
}
