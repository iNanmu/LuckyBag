package org.serverct.ersha.bisai.luckybag.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class JavaScript {

    private static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    /**
     * @param player 玩家
     * @param list 列表
     * @return boolean
     */
    public static boolean eval(Player player, List<String> list) throws ScriptException {
        //1.7.10则无法使用
        if (Bukkit.getServer().getVersion().contains("1.7")){
            return true;
        }
        int size = 0;
        for (String a : list){
            String[] args = a.split("#");
            if (a(player, args[0])){
                size++;
            }else{
                player.sendMessage(args[1].replace("&", "§"));
                break;
            }
        }
        return size >= list.size();
    }

    private static boolean a(Player player, String text) throws ScriptException {
        return (boolean) jse.eval(PlaceholderAPI.setPlaceholders(player, text));
    }
}
