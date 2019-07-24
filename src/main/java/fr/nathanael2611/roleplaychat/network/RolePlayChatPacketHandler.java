package fr.nathanael2611.roleplaychat.network;

import fr.nathanael2611.roleplaychat.RolePlayChat;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class RolePlayChatPacketHandler {

    private static SimpleNetworkWrapper network;

    public static void initPackets(){

        network = NetworkRegistry.INSTANCE.newSimpleChannel(RolePlayChat.MOD_ID.toUpperCase());
        network.registerMessage(
                PacketSetRolePlayName.Handler.class,
                PacketSetRolePlayName.class,
                0,
                Side.SERVER
        );

    }

    public static SimpleNetworkWrapper getNetwork() {
        return network;
    }
}
