package org.serverct.ersha.bisai.luckybag.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.serverct.ersha.bisai.luckybag.extension.AttributePlus;

/**
 * @author ersha
 * @date 2020/1/9
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent evt){
        Player player = evt.getPlayer();
        //加载属性
        AttributePlus.loadAttribute(player.getName());
    }
}
