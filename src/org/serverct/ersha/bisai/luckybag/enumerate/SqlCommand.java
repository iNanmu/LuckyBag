package org.serverct.ersha.bisai.luckybag.enumerate;

/**
 * @author ersha
 * @date 2020/1/3
 */
public enum SqlCommand {

    /**
     * 新建表
     */
    CREATE_TABLE(
            "create table LuckyBag(player varchar(20), value int(20))"
    ),

    /**
     * 插入记录
     */
    INSERT_PLAYER_VALUE(
            "insert into LuckyBag(player, value) values(?,?)"
    ),

    /**
     * 更新记录
     */
    UPDATE_PLAYER_VALUE(
            "update LuckyBag set `value` = ? where `player` = ?"
    ),

    /**
     * 获取记录
     */
    SELECT_PLAYER_VALUE(
            "select value from LuckyBag where `player` = ?"
    );

    private String string;

    SqlCommand(String string) {
        this.string = string;
    }

    public String commandToString(){
        return this.string;
    }
}
