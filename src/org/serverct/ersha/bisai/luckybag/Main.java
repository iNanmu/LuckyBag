package org.serverct.ersha.bisai.luckybag;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.serverct.ersha.bisai.luckybag.database.Database;
import org.serverct.ersha.bisai.luckybag.database.SqlManager;
import org.serverct.ersha.bisai.luckybag.extension.AttributePlus;
import org.serverct.ersha.bisai.luckybag.hook.HookPlaceholderAPI;
import org.serverct.ersha.bisai.luckybag.listener.Interaction;
import org.serverct.ersha.bisai.luckybag.listener.PlayerJoinListener;
import org.serverct.ersha.bisai.luckybag.net.bstats.Metrics;
import org.serverct.ersha.bisai.luckybag.util.Items;

import java.io.File;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class Main extends JavaPlugin implements Listener {

    private static Main instance;
    public static Database database;
    public static Main getInstance(){
        return instance;
    }
    public static Database getDatabase(){
        return database;
    }

    @Override
    public void onEnable() {
        instance = this;
        new Metrics(this);
        Bukkit.getConsoleSender().sendMessage("§f[§6§l!§f] 正在加载插件!");
        Bukkit.getPluginCommand("lb").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new Interaction(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        //加载配置
        this.saveDefaultConfig();
        this.reloadConfig();
        this.loadFile();
        //加载物品
        new Items().loadItemData();
        //兼容插件
        this.hook();
        new SqlManager().enable();
        Bukkit.getConsoleSender().sendMessage("§f[§6§l!§f] 加载完毕!");
    }

    @Override
    public void onDisable(){
        //断开数据库
        SqlManager.getInstance().shutdown();
    }

    /**
     * 兼容插件
     */
    private void hook() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage("§f[§a§l!§f] PlaceholderAPI §a§l已兼容");
            new HookPlaceholderAPI().register();
        }
        if (Bukkit.getPluginManager().getPlugin("AttributePlus") != null) {
            if (!Bukkit.getPluginManager().getPlugin("AttributePlus").getDescription().getVersion().contains("1.6")) {
                Bukkit.getConsoleSender().sendMessage("§f[§a§l!§f] AttributePlus §a§l已兼容");
                new AttributePlus();
            }
        }
    }

    /**
     * 加载配置
     */
    private void loadFile(){
        File file = new File(Main.getInstance().getDataFolder()+"/ItemStorage.yml");
        if (!file.exists()){
            saveResource("ItemStorage.yml", true);
        }
    }
}
