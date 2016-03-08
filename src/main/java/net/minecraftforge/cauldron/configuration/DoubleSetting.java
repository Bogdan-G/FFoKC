package net.minecraftforge.cauldron.configuration;

public class DoubleSetting extends Setting<Double>
{
    private Double value;
    private ConfigBase config;
    
    public DoubleSetting(ConfigBase config, String path, Double def, String description)
    {
        super(path, def, description);
        this.value = def;
        this.config = config;
    }

    @Override
    public Double getValue()
    {
        return value;
    }

    @Override
    public void setValue(String value)
    {
        this.value = org.apache.commons.lang.math.NumberUtils.toDouble(value, def);
        config.set(path, this.value);
    }
}

