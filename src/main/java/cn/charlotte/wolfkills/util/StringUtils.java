package cn.charlotte.wolfkills.util;

import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;

import java.util.Map;

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
        if (num < game.getPlayers().size() && num > 0) {
            for (Map.Entry<Long, PlayerData> entry : game.getPlayers().entrySet()) {
                if (entry.getValue().getNum() == num) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
