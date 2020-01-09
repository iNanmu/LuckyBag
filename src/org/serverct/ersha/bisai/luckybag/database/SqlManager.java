package org.serverct.ersha.bisai.luckybag.database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import org.bukkit.Bukkit;
import org.serverct.ersha.bisai.luckybag.Config;
import org.serverct.ersha.bisai.luckybag.Main;
import org.serverct.ersha.bisai.luckybag.enumerate.ChangedType;
import org.serverct.ersha.bisai.luckybag.enumerate.SqlCommand;
import org.serverct.ersha.bisai.luckybag.extension.AttributePlus;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ersha
 * @date 2020/1/3
 */
public class SqlManager implements Database {

    private PreparedStatement preparedStatement;
    private static Connection connection;
    public static SqlManager instance;

    public static SqlManager getInstance(){
        return instance == null ? new SqlManager() : instance;
    }

    /**
     * 启动MySql
     */
    public void enable() {
        Boolean enable = (Boolean) Config.getSetting("Setting.MySQL.enable");
        if (enable) {
            this.connectDataBase();
            this.checkTable();
            Main.database = this;
            return;
        }
        Main.database = new YamlManager();
    }

    /**
     * 连接数据库
     * **/
    private void connectDataBase(){
        String sql = (String) Config.getSetting("Setting.MySQL.sql");
        String user = (String) Config.getSetting("Setting.MySQL.root");
        String password = (String) Config.getSetting("Setting.MySQL.password");
        try {
            connection = (Connection) DriverManager.getConnection(sql, user, password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 断开连接
     */
    public void shutdown() {
        if (instance != null) {
            try {
                connection.close();
                Bukkit.getConsoleSender().sendMessage("§f[§6§l!§f] 数据库已正常断开连接");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检测数据表
     * 数据表不存在则自动新建
     */
    private void checkTable(){
        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet resultSet = connection.getMetaData().getTables(null,null, "LuckyBag", null);
            if (!resultSet.next()) {
                statement.executeUpdate(SqlCommand.CREATE_TABLE.commandToString());
                Bukkit.getConsoleSender().sendMessage("§f[§c§l!§f] 数据表不存在,已自动新建数据表");
            }else {
                Bukkit.getConsoleSender().sendMessage("§f[§6§l!§f] 数据表正常");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 玩家数据变动
     * @param player 玩家
     * @param type 操作类型
     * @param value 值
     */
    @Override
    public void changedPlayerData(String player, ChangedType type, int value) {
        try {
            int newValue = 0;
            switch (type) {
                case ADD:
                    newValue = getPlayerValue(player) + value;
                    break;
                case TAKE:
                    newValue = getPlayerValue(player) - value;
                    break;
                case SET:
                    newValue = value;
                    break;
                default:
                    break;
            }
            if (getPlayerValue(player) > 0) {
                preparedStatement = connection.prepareStatement(SqlCommand.UPDATE_PLAYER_VALUE.commandToString());
                preparedStatement.setInt(1, newValue);
                preparedStatement.setString(2, player);
            }else{
                preparedStatement = connection.prepareStatement(SqlCommand.INSERT_PLAYER_VALUE.commandToString());
                preparedStatement.setString(1, player);
                preparedStatement.setInt(2, newValue);
            }
            preparedStatement.executeUpdate();
            AttributePlus.loadAttribute(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取玩家当前福气值
     * @param player 玩家
     * @return int
     */
    @Override
    public int getPlayerValue(String player) {
        int v = 0;
        try {
            preparedStatement = connection.prepareStatement(SqlCommand.SELECT_PLAYER_VALUE.commandToString());
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                v = resultSet.getInt("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
}
