package fr.nathanael2611.roleplaychat;

import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import net.minecraft.entity.player.EntityPlayer;

public class RolePlayNames {
    public static final String ROLEPLAYNAME_KEY = "roleplaychat:roleplayername";
    public static String getName(EntityPlayer player) {
        DatabaseReadOnly playerData = Databases.getPlayerData(player);
        if(player.world.isRemote) playerData = ClientDatabases.getPersonalPlayerData();
        if(playerData.containsString(ROLEPLAYNAME_KEY)){
            return playerData.getString(ROLEPLAYNAME_KEY);
        }
        return player.getName();
    }

    public static void setName(EntityPlayer player, String name){
        Databases.getPlayerData(player).setString(ROLEPLAYNAME_KEY, name);
    }
}
