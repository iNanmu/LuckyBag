package org.serverct.ersha.bisai.luckybag.util;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.serverct.ersha.bisai.luckybag.Config;
import org.serverct.ersha.bisai.luckybag.Main;
import org.serverct.ersha.bisai.luckybag.Map;
import org.serverct.ersha.bisai.luckybag.extension.AttributePlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class Util {

    /**
     * 判断玩家背包是否有空着的背包格
     */
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

    /**
     * 替换
     */
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

    /**
     * 随机
     */
    private static Integer random(String[] args){
        return new Random().nextInt(Integer.parseInt(args[1])-Integer.parseInt(args[0]))+Integer.parseInt(args[0]);
    }

    /**
     * 玩家个人信息
     * @param player 要查询的那个玩家
     */
    public static void sendInfo(CommandSender player){
        String group = Map.AttributeGroup.get(player);
        int dataValue = Main.getDatabase().getPlayerValue(player.getName());
        ArrayList<String> arrayList = new ArrayList<>();
        List<String> attribute = AttributePlus.yaml.getStringList("AttributeGroup." + group);
        arrayList.add("§e玩家个人信息:");
        arrayList.add(" §3福气值: §c"+dataValue);
        arrayList.add(" ");
        arrayList.add("§e福气属性加成:");
        arrayList.add(" §3属性组: §6"+(group == null ? "§7未激活" : group));
        arrayList.add(" §3属性内容:");
        if (group != null) {
            for (String str : attribute) {
                arrayList.add("   §f- " + str.replace("&", "§"));
            }
        }else{
            arrayList.add("   §f- 无属性加成");
        }
        for (String str : arrayList){
            player.sendMessage(str);
        }
    }
}
