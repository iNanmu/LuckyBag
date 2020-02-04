package org.serverct.ersha.bisai.luckybag.extension;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.serverct.ersha.bisai.luckybag.Main;
import org.serverct.ersha.bisai.luckybag.Map;
import org.serverct.ersha.jd.AttributeAPI;

import java.io.File;
import java.util.List;

/**
 * AttributePlus 属性插件版本要求 1.7.0 以上版本
 * @author ersha
 * @date 2020/1/9
 */
public class AttributePlus {

    private static File file = new File(Main.getInstance().getDataFolder() + "/attribute.yml");
    public static YamlConfiguration yaml;
    private static Boolean hook = false;

    public AttributePlus() {
        if (!file.exists()) {
            Main.getInstance().saveResource("attribute.yml", true);
        }
        yaml = YamlConfiguration.loadConfiguration(file);
        hook = true;
    }

    /**
     * 加载福气属性
     * @param name 玩家名
     */
    public static void loadAttribute(String name) {
        if (hook && yaml.getBoolean("Setting.enable")) {
            int dataValue = Main.getDatabase().getPlayerValue(name);
            List<String> list = yaml.getStringList("Setting.list");
            for (String str : list) {
                String[] args = str.split("#");
                int value = Integer.parseInt(args[0]);
                List<String> attribute = yaml.getStringList("AttributeGroup." + args[1]);
                if (dataValue >= value) {
                    if (Bukkit.getPlayer(name).isOnline()) {
                        Player player = Bukkit.getPlayer(name);
                        //调用属性API
                        AttributeAPI.addAttribute(player, "LuckyBag", attribute, false);
                        Map.AttributeGroup.put(player, args[1]);
                    }
                }
            }
        }
    }
}
