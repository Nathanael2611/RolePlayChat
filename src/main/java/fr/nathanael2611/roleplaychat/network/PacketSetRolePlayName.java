package fr.nathanael2611.roleplaychat.network;

import fr.nathanael2611.roleplaychat.RolePlayNames;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSetRolePlayName implements IMessage {

    private String name;

    public PacketSetRolePlayName(){}

    public PacketSetRolePlayName(String name){
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.name);
    }

    public static class Handler implements IMessageHandler<PacketSetRolePlayName, IMessage> {

        @Override
        public IMessage onMessage(PacketSetRolePlayName message, MessageContext ctx) {
            RolePlayNames.setName(
                    ctx.getServerHandler().player,
                    message.name
            );
            System.out.println(ctx.getServerHandler().player.getName());
            return null;
        }
    }

}
