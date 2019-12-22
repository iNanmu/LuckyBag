package org.serverct.ersha.bisai.luckybag;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.serverct.ersha.bisai.luckybag.listener.Interaction;
import org.serverct.ersha.bisai.luckybag.util.Items;

import java.io.File;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class Main extends JavaPlugin implements Listener {

    private static Main instance;
    public static Main getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        //加载配置
        saveDefaultConfig();
        reloadConfig();
        loadFile();
        Bukkit.getConsoleSender().sendMessage("§f[§6§l!§f] 正在加载插件!");
        Bukkit.getPluginCommand("lb").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new Interaction(), this);
        //加载物品
        new Items().loadItemData();
        Bukkit.getConsoleSender().sendMessage("§f[§6§l!§f] 加载完毕!");
    }

    @Override
    public void onDisable(){
        
    }

    //加载配置
    private void loadFile(){
        File file = new File(Main.getInstance().getDataFolder()+"/ItemStorage.yml");
        if (!file.exists()){
            saveResource("ItemStorage.yml", true);
        }
    }
}
