package fr.nathanael2611.roleplaychat.event;

import fr.nathanael2611.roleplaychat.client.config.ChatType;
import fr.nathanael2611.roleplaychat.client.config.EnumChatTypes;
import fr.nathanael2611.roleplaychat.RolePlayChat;
import fr.nathanael2611.roleplaychat.RolePlayNames;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ChatHandler {

    public static void sendChatMessageByPlayer(EntityPlayer player, ChatType type, String message){
        EntityPlayer[] players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().toArray(new EntityPlayer[0]);
        List<EntityPlayer> playersInRadius = new ArrayList<>();
        final int DISTANCE = type.getDistance();
        String format = type.getFormat();
        if(format.contains("{player}"))  format = format.replace("{player}", player.getName());
        if(format.contains("{rpname}"))  format = format.replace("{rpname}", RolePlayNames.getName(player));
        if(format.contains("{message}")) format = format.replace("{message}", message);
        if(DISTANCE == 0){
            playersInRadius.addAll(Arrays.asList(players));
        }else{
            for(EntityPlayer entityPlayer : players){
                if(
                        player.getPosition().getDistance(
                                entityPlayer.getPosition().getX(),
                                entityPlayer.getPosition().getY(),
                                entityPlayer.getPosition().getZ()
                        ) <= type.getDistance()
                ){
                    playersInRadius.add(entityPlayer);
                }
            }
        }
        for(EntityPlayer entityPlayer : playersInRadius){
            entityPlayer.sendMessage(
                    new TextComponentString(format)
            );
        }
    }

    @SubscribeEvent
    public static void onChat(ServerChatEvent e){
        e.setCanceled(true);
        EntityPlayer player = e.getPlayer();
        String entireMessage = e.getMessage();
        ChatType type = RolePlayChat.getConfig().getChatType(EnumChatTypes.NORMAL);
        for(ChatType chatType : RolePlayChat.getConfig().getChatTypes()){
            if(!chatType.getPrefix().equals("")){
                if(entireMessage.startsWith(chatType.getPrefix())){
                    type = chatType;
                    break;
                }
            }else{
                type = chatType;
            }
        }
        entireMessage = entireMessage.substring(type.getPrefix().length());
        sendChatMessageByPlayer(player, type, entireMessage);
    }
}