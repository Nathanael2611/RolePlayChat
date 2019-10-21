package fr.nathanael2611.roleplaychat;

import fr.nathanael2611.roleplaychat.client.config.ChatConfig;
import fr.nathanael2611.roleplaychat.command.CommandSetRPName;
import fr.nathanael2611.roleplaychat.network.RolePlayChatPacketHandler;
import fr.nathanael2611.roleplaychat.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.IOException;

@Mod(modid = "roleplaychat", version = "1.0.0")
public class RolePlayChat {

    public static final String MOD_ID = "roleplaychat";
    public static final String PREFIX = "§c[RolePlayChat]§6 ";

    @Mod.Instance
    public static RolePlayChat instance;

    @SidedProxy(
            serverSide = "fr.nathanael2611.roleplaychat.proxy.ServerProxy",
            clientSide = "fr.nathanael2611.roleplaychat.proxy.ClientProxy"
    )
    private static CommonProxy proxy;

    private static ChatConfig config;

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent e)
    {
        proxy.preInitialization(e.getSuggestedConfigurationFile());
        RolePlayChatPacketHandler.initPackets();
        if(!e.getSuggestedConfigurationFile().exists())
        {
            try
            {
                e.getSuggestedConfigurationFile().createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try
        {
            config = new ChatConfig(e.getSuggestedConfigurationFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void initialization(FMLInitializationEvent e)
    {
        proxy.initialization();
    }


    public static ChatConfig getConfig()
    {
        return config;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent e)
    {
        e.registerServerCommand(new CommandSetRPName());
    }
}
