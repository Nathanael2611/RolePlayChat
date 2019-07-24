package fr.nathanael2611.roleplaychat.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

import java.io.File;

public class ClientProxy extends CommonProxy {

    public static final KeyBinding KEY_RPCHAT_GUI = new KeyBinding("RP-Chat GUI", Keyboard.KEY_G, "RP-Chat");

    @Override
    public void preInitialization(File configFile) {
        super.preInitialization(configFile);
        ClientRegistry.registerKeyBinding(KEY_RPCHAT_GUI);
    }

    @Override
    public void initialization() {
        super.initialization();
    }
}
