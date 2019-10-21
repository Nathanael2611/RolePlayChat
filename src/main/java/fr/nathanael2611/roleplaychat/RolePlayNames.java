package fr.nathanael2611.roleplaychat;

import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import net.minecraft.entity.player.EntityPlayer;

public class RolePlayNames {

    public static final String ROLEPLAYNAME_KEY = "roleplaychat:roleplayname";

    public static String getName(EntityPlayer player)
    {
        DatabaseReadOnly playerData = player.world.isRemote ? ClientDatabases.getPersonalPlayerData() : Databases.getPlayerData(player);
        return playerData.isString(ROLEPLAYNAME_KEY) ? playerData.getString(ROLEPLAYNAME_KEY) : player.getName();
    }

    public static void setName(EntityPlayer player, String name)
    {
        Databases.getPlayerData(player).setString(ROLEPLAYNAME_KEY, name);
    }
}
