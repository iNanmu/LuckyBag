package org.serverct.ersha.bisai.luckybag;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.serverct.ersha.bisai.luckybag.util.Items;
import org.serverct.ersha.bisai.luckybag.util.Util;

/**
 * @author ersha
 * @date 2019/12/8
 */
public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage("此指令无法从后台输入");
            return true;
        }

        Player player = (Player)commandSender;
        if (!player.isOp()){
            player.sendMessage("你没有权限使用此指令");
            return true;
        }

        if (strings.length == 0){
            player.sendMessage("§6§lLuckyBag - 福袋  By.二傻(楠木i)");
            player.sendMessage("§f/lb itemid §8- §7获取已加载完毕的物品ID列表");
            player.sendMessage("§f/lb give <玩家> <物品ID> <数量> §8- §7给指定玩家已加载完毕的物品");
            player.sendMessage("§f/lb reload §8- §7重载插件(物品同步重新加载)");
        }

        if (strings.length == 4 && strings[0].equalsIgnoreCase("give")){
            if (Bukkit.getOfflinePlayer(strings[1]).isOnline()){
                Player players = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                ItemStack itemStack = Items.items(strings[2]);
                if (itemStack != null){
                    itemStack.setAmount(Integer.parseInt(strings[3]));
                    player.sendMessage("§f[§6§l!§f] §f执行成功");
                    players.sendMessage("§f[§6§l!§f] §f管理员发送了 "+strings[2]+"*§c"+strings[3]+" §f到你背包");
                    if (Util.playerInventory(players)){
                        players.getInventory().addItem(itemStack);
                    }else{
                        players.getWorld().dropItem(players.getLocation(), itemStack);
                    }
                }
            }
        }

        if (strings.length == 1 && strings[0].equalsIgnoreCase("itemid")){
            Items.sendItemsList(player);
        }

        if (strings.length == 1 && strings[0].equalsIgnoreCase("reload")){
            Main.getInstance().reloadConfig();
            new Items().loadItemData();
            player.sendMessage("§f[§6§l!§f] §f重载完毕");
        }

        return false;
    }
}
