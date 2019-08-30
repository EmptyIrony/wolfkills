package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlManager {
    private Connection conn;

    public MysqlManager() {
        String ip = "127.0.0.1";
        String database = "bot";
        String user = "root";
        String passwd = "1234";
        String url = "jdbc:mysql://" + ip + "/" + database + "?useSSL=false";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, passwd);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS WolfKills(`QQ` bigint(255),`wolfWins` INT,`humanWins` INT,`Scenes` INT,PRIMARY key (`QQ`));");
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settlement(Game game) {
        if (game.getWinner().equalsIgnoreCase("human")) {
            for (PlayerData data : game.getHumanTeam()) {
                if (isExits(data.getQq())) {
                    try {
                        PreparedStatement ps = conn.prepareStatement("UPDATE WolfKills SET humanwins=? where QQ=?;");
                        ps.setInt(1, getHumanWins(data.getQq()) + 1);
                        ps.setLong(2, data.getQq());
                        ps.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO WolfKills VALUES (?,?,?,?);");
                        ps.setLong(1, data.getQq());
                        ps.setInt(2, 0);
                        ps.setInt(3, 1);
                        ps.setInt(4, 0);
                        ps.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            for (PlayerData data : game.getWolfTeam()) {
                if (isExits(data.getQq())) {
                    try {
                        PreparedStatement ps = conn.prepareStatement("UPDATE WolfKills SET wolfwins=? where QQ=?;");
                        ps.setInt(1, getWolfWins(data.getQq()) + 1);
                        ps.setLong(2, data.getQq());
                        ps.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO WolfKills VALUES (?,?,?,?);");
                        ps.setLong(1, data.getQq());
                        ps.setInt(2, 1);
                        ps.setInt(3, 0);
                        ps.setInt(4, 0);
                        ps.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean isExits(long qq) {
        boolean exits;

        try {
            PreparedStatement ps = conn.prepareStatement("select QQ from wolfkills where QQ=?");
            ps.setLong(1, qq);
            ResultSet rs = ps.executeQuery();
            exits = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return exits;
    }

    private int getWolfWins(long qq) {
        int num;
        try {
            PreparedStatement ps = conn.prepareStatement("select `WolfWins` from wolfkills where QQ=?;");
            ps.setLong(1, qq);
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("qq");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return num;
    }

    private int getHumanWins(long qq) {
        int num;
        try {
            PreparedStatement ps = conn.prepareStatement("select `HumanWins` from wolfkills where QQ=?;");
            ps.setLong(1, qq);
            ResultSet rs = ps.executeQuery();
            rs.next();
            num = rs.getInt("qq");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return num;
    }
}
