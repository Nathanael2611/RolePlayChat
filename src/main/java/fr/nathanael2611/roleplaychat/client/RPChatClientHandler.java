package fr.nathanael2611.roleplaychat.client;

import fr.nathanael2611.roleplaychat.RolePlayNames;
import fr.nathanael2611.roleplaychat.client.gui.GuiChangeRPName;
import fr.nathanael2611.roleplaychat.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class RPChatClientHandler {

    @SubscribeEvent
    public static void keyInputEvent(InputEvent.KeyInputEvent e) {
        if(ClientProxy.KEY_RPCHAT_GUI.isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(
                    new GuiChangeRPName(
                            RolePlayNames.getName(
                                    Minecraft.getMinecraft().player
                            )
                    )
            );
        }
    }

}
