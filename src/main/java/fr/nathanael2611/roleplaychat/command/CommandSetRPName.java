package fr.nathanael2611.roleplaychat.command;

import fr.nathanael2611.roleplaychat.RolePlayChat;
import fr.nathanael2611.roleplaychat.RolePlayNames;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommandSetRPName extends CommandBase {

    public static final TextComponentString CORRECT_USAGE = new TextComponentString(
            RolePlayChat.PREFIX + "§cUtilisations correctes: /roleplayname <nouveau_nom>, /roleplayname <get> <EntityPlayer>, /roleplayname set <EntityPlayer> <nouveau_nom>."
    );

    @Override
    public String getName() {
        return "roleplayname";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        if(args.length == 1){
            RolePlayNames.setName(player, args[0]);
            player.sendMessage(
                    new TextComponentString(
                            RolePlayChat.PREFIX + "Votre nom RP a été définit à " + args[0] + "."
                    )
            );
        }else if(args.length >= 2){
            EntityPlayer target = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[1]);
            if(target == null){
                player.sendMessage(
                        new TextComponentString("§cCannot resolve player '" + args[1] + "'. Please specify a valid player.")
                );
                return;
            }
            if(args[0].equalsIgnoreCase("set")){
                if(args.length < 3){
                    player.sendMessage(
                            CORRECT_USAGE
                    );
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 2; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }
                RolePlayNames.setName(
                        target,
                        stringBuilder.toString().substring(stringBuilder.toString().length()-1)
                );
            }else if (args[0].equalsIgnoreCase("get")){
                if(args.length == 2){
                    String rpName = RolePlayNames.getName(target);
                    if(rpName.equalsIgnoreCase(target.getName())){
                        player.sendMessage(
                                new TextComponentString(
                                        "§cThe player '" + target.getName() + "' has no roleplay-name."
                                )
                        );
                        return;
                    }
                    player.sendMessage(
                            new TextComponentString(
                                    RolePlayChat.PREFIX + "The " + target.getName() + "'s roleplay-name is §e'" + rpName + "'§6."
                            )
                    );
                }else{
                    player.sendMessage(
                            CORRECT_USAGE
                    );
                    return;
                }
            }else{
                player.sendMessage(
                        CORRECT_USAGE
                );
                return;
            }
        }
    }
}
