package org.serverct.ersha.bisai.luckybag.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.serverct.ersha.bisai.luckybag.Config;

import java.util.Random;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class Util {

    public static boolean playerInventory(Player player){
        PlayerInventory playerInventory = player.getInventory();
        for (int i = 0;i<35;i++){
            if (playerInventory.getItem(i) == null){
                return playerInventory.getItem(i) == null || playerInventory.getItem(i).getType().equals(Material.AIR) ;
            }
        }
        player.sendMessage(Config.getMessage("playerInventory"));
        return false;
    }

    static Integer replaceInteger(String string){
        if (string.contains("<r:")){
            String[] args1 = string.split("<r:");
            if (args1[1].contains(">")){
                String[] args2 = args1[1].split(">");
                String[] args3 = args2[0].split("-");
                return random(args3);
            }
        }
        return Integer.parseInt(string);
    }

    private static Integer random(String[] args){
        return new Random().nextInt(Integer.parseInt(args[1])-Integer.parseInt(args[0]))+Integer.parseInt(args[0]);
    }
}
