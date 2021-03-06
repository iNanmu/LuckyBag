package org.serverct.ersha.bisai.luckybag.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.serverct.ersha.bisai.luckybag.Main;
import org.serverct.ersha.bisai.luckybag.util.Cooling;
import org.serverct.ersha.bisai.luckybag.util.JavaScript;
import org.serverct.ersha.bisai.luckybag.util.Reward;

import java.util.List;

/**
 * @author ersha
 * @date 2019/12/1 create
 * @date 2020/02/04 update
 */
public class Interaction implements Listener{

    @EventHandler
    public void onInteraction(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.getItemInHand().getType() != Material.AIR) {
                ItemStack itemStack = player.getItemInHand();
                if (itemStack.getItemMeta().hasDisplayName()) {
                    String name = itemStack.getItemMeta().getDisplayName();
                    String project = getProject(name);
                    if (!project.equals("无")) {
                        Integer cd = Main.getInstance().getConfig().getInt("Items.List."+project+".cooling");
                        Cooling cooling = new Cooling(player, project, cd);
                        if (cooling.isBagCooling()) {
                            //条件列表
                            List<String> Condition = Main.getInstance().getConfig().getStringList("Items.List." + project + ".Condition");
                            if (JavaScript.eval(player, Condition)) {
                                //奖励列表
                                List<String> reward = Main.getInstance().getConfig().getStringList("Items.List." + project + ".Reward");
                                new Reward(player, reward);
                                cooling.loadBagCooling();
                                takeItems(player, itemStack);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取项目名
     */
    private String getProject(String name){
        for (String project : Main.getInstance().getConfig().getConfigurationSection("Items.List").getKeys(false)){
            String names = Main.getInstance().getConfig().getString("Items.List."+project+".Name");
            if (name.replace("&", "§").equals(names.replace("&", "§"))){
                return project;
            }
        }
        return "无";
    }

    /**
     * 扣除物品
     */
    private void takeItems(Player player, ItemStack itemStack){
        if (itemStack.getAmount() > 1){
            itemStack.setAmount(itemStack.getAmount()-1);
        }else{
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
        }
    }
}
