package org.serverct.ersha.bisai.luckybag.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.serverct.ersha.bisai.luckybag.Main;

/**
 * @author ersha
 * @date 2020/1/3
 */
public class HookPlaceholderAPI extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "luckybag";
    }

    @Override
    public String getAuthor() {
        return "erSha";
    }

    @Override
    public String getVersion() {
        return Main.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (p.isOnline()) {
            if ("value".equals(params)) {
                return String.valueOf(Main.getDatabase().getPlayerValue(p.getName()));
            }
        }
        return null;
    }
}
