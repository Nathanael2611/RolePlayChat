package fr.nathanael2611.roleplaychat.command;

import fr.nathanael2611.roleplaychat.RolePlayChat;
import fr.nathanael2611.roleplaychat.RolePlayNames;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.List;

public class CommandSetRPName extends CommandBase {

    @Override
    public String getName()
    {
        return "roleplaychat";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        String correctUsage = "/roleplaychat reload OR /roleplaychat <set/get> <player> [<name>]";
        if(args.length > 0)
        {
            if(args[0].equalsIgnoreCase("reload"))
            {
                try
                {
                    RolePlayChat.getConfig().reload();
                    sender.sendMessage(new TextComponentString(RolePlayChat.PREFIX + TextFormatting.GREEN + "La configuration a bien été rechargée"));
                } catch (Exception e)
                {
                    sender.sendMessage(new TextComponentString(e.getMessage()));
                }
            } else {
                if(args.length > 1)
                {
                    EntityPlayer player = getPlayer(server, sender, args[1]);
                    if(args[0].equalsIgnoreCase("get")) sender.sendMessage(new TextComponentString(RolePlayChat.PREFIX + TextFormatting.GOLD + "Le nom RP de " + player.getName() + " est '" + TextFormatting.RED + RolePlayNames.getName(player) + TextFormatting.GOLD + "'"));
                    else if(args[0].equalsIgnoreCase("set"))
                    {
                        if(args.length > 2)
                        {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 2; i < args.length; i++) stringBuilder.append(" ").append(args[i]);
                            RolePlayNames.setName(player, stringBuilder.toString().substring(1));
                            sender.sendMessage(new TextComponentString(RolePlayChat.PREFIX + TextFormatting.GOLD + "Le nom RP de " + player.getName() + " a été définit à '" + TextFormatting.RED + stringBuilder.toString().substring(1) + TextFormatting.GOLD + "'"));
                        } else throw new WrongUsageException(correctUsage);
                    }
                } else throw new WrongUsageException(correctUsage);
            }
        } else throw new WrongUsageException(correctUsage);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if(args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "set", "get", "reload");
        } else
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
    }
}
