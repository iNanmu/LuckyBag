package org.serverct.ersha.bisai.luckybag.util;

import org.bukkit.entity.Player;
import org.serverct.ersha.bisai.luckybag.Config;
import org.serverct.ersha.bisai.luckybag.Map;
import org.serverct.ersha.bisai.luckybag.runs.CoolingRun;

/**
 * @author ersha
 * @date 2019/12/29
 */
public class Cooling {

    private Player player;
    private String bag;
    private Integer time;

    public Cooling(Player player, String bag, Integer time){
        this.player = player;
        this.bag = bag;
        this.time = time;
    }

    /**判断所使用的福袋是否在冷却**/
    public boolean isBagCooling(){
        if (Map.BagCooling.get(this.player.getName()+":"+this.bag) == null){
            return true;
        }
        player.sendMessage(Config.getMessage("bagCooling"));
        return false;
    }

    /**福袋冷却**/
    public void loadBagCooling(){
        Map.BagCooling.put(this.player.getName()+":"+this.bag, "0");
        new CoolingRun().coolingRun(this.player.getName(), this.bag, this.time);
    }
}
