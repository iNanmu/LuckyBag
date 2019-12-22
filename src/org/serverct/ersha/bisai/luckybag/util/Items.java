package org.serverct.ersha.bisai.luckybag.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.serverct.ersha.bisai.luckybag.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class Items{

    private static HashMap<String, ItemStack> itemHashMap = new HashMap<>();

    /**
     * @param project 项目名
     * @return ItemStack
     */
    public static ItemStack items(String project){
        if (itemHashMap.get(project) == null){
            ItemStack itemStack = new ItemStack(Material.AIR);
            Bukkit.getConsoleSender().sendMessage("§f[§c§l!§f] §f物品ID §6"+project+" §f不存在");
            return itemStack;
        }
        return itemHashMap.get(project);
    }

    /**
     * 加载 ItemStorage.yml 内的项目
     */
    public void loadItemData(){
        itemHashMap.clear();
        File file = new File(Main.getInstance().getDataFolder()+"/ItemStorage.yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        Bukkit.getConsoleSender().sendMessage("§f[§6§l!§f] §f正在载入 ItemStorage.yml 物品");
        //加载 ItemStorage.yml 内物品
        for (String project : yaml.getConfigurationSection("ItemStorage").getKeys(false)){

            int id = yaml.getInt("ItemStorage."+project+".Id");
            int ids = yaml.getInt("ItemStorage."+project+".Ids");
            String name = yaml.getString("ItemStorage."+project+".Name");
            List<String> list = yaml.getStringList("ItemStorage."+project+".Lore");
            List<String> itemFlag = yaml.getStringList("ItemStorage."+project+".ItemFlag");
            List<String> enchant = yaml.getStringList("ItemStorage."+project+".Enchant");
            //ItemStack
            ItemStack itemStack = new ItemStack(Material.getMaterial(id), 1, (short) ids);
            //设置itemMeta
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name.replace("&", "§"));
            itemMeta.setLore(loadLore(list));
            addItemFlags(itemMeta, itemFlag);
            addEnchant(itemMeta, enchant);
            itemStack.setItemMeta(itemMeta);
            //保存进HashMap
            itemHashMap.put(project, itemStack);

            Bukkit.getConsoleSender().sendMessage("§8§l - §f物品ID §6"+project+" §f载入完毕");
        }
        //加载福袋
        for (String project : Main.getInstance().getConfig().getConfigurationSection("Items.List").getKeys(false)){
            int id = Main.getInstance().getConfig().getInt("Items.List."+project+".Id");
            int ids = Main.getInstance().getConfig().getInt("Items.List."+project+".Ids");
            String name = Main.getInstance().getConfig().getString("Items.List."+project+".Name");
            List<String> list = Main.getInstance().getConfig().getStringList("Items.List."+project+".Lore");
            //ItemStack
            ItemStack itemStack = new ItemStack(Material.getMaterial(id), 1, (short) ids);
            //设置itemMeta
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name.replace("&", "§"));
            itemMeta.setLore(loadLore(list));
            itemStack.setItemMeta(itemMeta);
            //保存进HashMap
            itemHashMap.put(project, itemStack);
        }
    }

    /**
     * 用于给物品添加附魔
     * @param itemMeta itemMeta
     * @param list 附魔列表
     */
    private void addEnchant(ItemMeta itemMeta, List<String> list){
        for (String enchant : list){
            String[] args = enchant.split(",");
            itemMeta.addEnchant(Enchantment.getByName(args[0]), Integer.parseInt(args[1]), false);
        }
    }

    /**
     * 用于给物品添加ItemFlags标签
     * @param itemMeta itemMeta
     * @param list 标签列表
     */
    private void addItemFlags(ItemMeta itemMeta, List<String> list){
        for (String itemFlag : list){
            itemMeta.addItemFlags(ItemFlag.valueOf(itemFlag));
        }
    }

    /**
     * @param list Lore列表
     * @return ArrayList<String>
     */
    private ArrayList<String> loadLore(List<String> list){
        ArrayList<String> lists = new ArrayList<>();
        for (String lore : list){
            lists.add(lore.replace("&", "§"));
        }
        return lists;
    }

    //对指定玩家发送插件已加载的物品ID及物品名
    public static void sendItemsList(Player player){
        player.sendMessage("§6已加载的物品列表 §3>>>");
        for (String key : itemHashMap.keySet()){
            String name = items(key).getItemMeta().getDisplayName();
            player.sendMessage("§3ID: §f"+key+"§3[§6"+name+"§3]");
        }
    }
}
