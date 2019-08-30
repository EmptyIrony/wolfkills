package cn.charlotte.wolfkills.util;

import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;

public class StringUtils {
    public static boolean isNum(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static PlayerData getPlayerByNum(int num, Game game) {
        for (PlayerData playerData : game.getPlayers()) {
            if (playerData.getNum() == num) {
                return playerData;
            }
        }
        return null;
    }
}
