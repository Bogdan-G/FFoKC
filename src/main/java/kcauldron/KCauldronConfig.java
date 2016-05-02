package kcauldron;

import org.bukkit.configuration.file.YamlConfiguration;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.cauldron.configuration.*;

public class KCauldronConfig extends ConfigBase {
    public final BoolSetting commandEnable = new BoolSetting(this, "command.enable", false, "Enable KCauldron command");

    public BoolSetting loggingMaterialInjection = new BoolSetting(this, "logging.materialInjection", true, "Log material injection event");
    public BoolSetting loggingClientModList = new BoolSetting(this, "logging.clientModList", true, "Print client's mod list during attempt to join");
        
    public BoolSetting commonAllowNetherPortal = new BoolSetting(this, "common.allowNetherPortalBesidesOverworld", false, "Allow nether portals in dimensions besides overworld");
            
    public IntSetting commonMaxCoordOverworld = new IntSetting(this, "common.MaxCoordinatesInOverworld", 30000000, "Max Coordinates In Overworld (XZ)");
    public DoubleSetting commonMovedTooQuicklyThreshold = new DoubleSetting(this, "common.MovedTooQuicklyThreshold", 250.0D, "Moved too quickly limit configurable");
    public DoubleSetting commonMovedWronglyThreshold = new DoubleSetting(this, "common.MovedWronglyThreshold", 0.1625D, "Moved wrongly limit configurable");
    public BoolSetting commonFastLeavesDecayEnable = new BoolSetting(this, "common.fastLeavesDecay.enable", false, "Enable fast decaying of leaves, not affects drop chanches /etc");
    public IntSetting commonFastLeavesDecayMinTickTime = new IntSetting(this, "common.fastLeavesDecay.minTickTime", 5, "Minimal amount of tick between block updates");
    public IntSetting commonFastLeavesDecayMaxTickTime = new IntSetting(this, "common.fastLeavesDecay.maxTickTime", 10, "Minimal amount of tick between block updates");
    public BoolSetting commonReduceBlockRedstoneTorchONC = new BoolSetting(this, "common.ReduceBlockRedstoneTorchONC", false, "Reduce update block Redstone Torch");
    
    public KCauldronConfig() {
        super("kcauldron.yml", "kc");
        register(commandEnable);
        register(loggingMaterialInjection);
        register(loggingClientModList);
        register(commonAllowNetherPortal);
        register(commonMaxCoordOverworld);
        register(commonMovedTooQuicklyThreshold);
        register(commonMovedWronglyThreshold);
        register(commonFastLeavesDecayEnable);
        register(commonFastLeavesDecayMinTickTime);
        register(commonFastLeavesDecayMaxTickTime);
        register(commonReduceBlockRedstoneTorchONC);
        load();
    }

    private void register(Setting<?> setting) {
        settings.put(setting.path, setting);
    }

    @Override
    public void registerCommands() {
        if (commandEnable.getValue()) {
            super.registerCommands();
        }
    }

    @Override
    protected void addCommands() {
        //commands.put(commandName, new KCauldronCommand());
    }

    @Override
    protected void load() {
        try {
            config = YamlConfiguration.loadConfiguration(configFile);
            String header = "";
            StringBuilder headerSB = new StringBuilder(header);
            for (Setting<?> toggle : settings.values()) {
                if (!toggle.description.equals(""))
                    headerSB.append("Setting: ").append(toggle.path).append(" Default: ").append(toggle.def).append(" # ").append(toggle.description).append("\n");

                config.addDefault(toggle.path, toggle.def);
                settings.get(toggle.path).setValue(config.getString(toggle.path));
            }
            header = String.valueOf(headerSB);
            config.options().header(header);
            config.options().copyDefaults(true);
            save();
        } catch (Exception ex) {
            MinecraftServer.getServer().logSevere(
                    "Could not load " + this.configFile);
            ex.printStackTrace();
        }
    }
}
