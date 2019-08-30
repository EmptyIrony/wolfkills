package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.Vocation;

import java.util.*;

public class GameManager {

    //发送身份
    public static void sendVocations(Game game) {
        Main.CQ.setGroupWholeBan(game.getGroup(),true);
        //判断游戏人数以填充身份数量
        Map<Vocation,Integer> VocationNumber = new HashMap<>();
        VocationNumber.put(Vocation.VILLAGER,3);
        VocationNumber.put(Vocation.WEREWOLF,2);
        VocationNumber.put(Vocation.PROPHET,1);
        VocationNumber.put(Vocation.WITCH,1);
        //8人以上时随机增加一名未被填充的角色
        if  (game.getPlayers().size() > 7) {
            Boolean finded = false;
            while (!finded) {
                List<Vocation> vocationadd = new ArrayList<>();
                vocationadd.add(Vocation.HUNTER);
                vocationadd.add(Vocation.KNIGHT);
                vocationadd.add(Vocation.BEAR);
                vocationadd.add(Vocation.DEFENDER);
                Collections.shuffle(vocationadd);
                if (VocationNumber.get(vocationadd.get(0)) == null) {
                    VocationNumber.put(vocationadd.get(0),1);
                    finded = true;
                }
            }
        }
        if (game.getPlayers().size() > 8) {
            VocationNumber.put(Vocation.WEREWOLF,3);
        }
        if (game.getPlayers().size() > 9) {
            VocationNumber.put(Vocation.VILLAGER,4);
        }
        if  (game.getPlayers().size() > 10) {
            Boolean finded = false;
            while (!finded) {
                List<Vocation> vocationadd = new ArrayList<>();
                vocationadd.add(Vocation.HUNTER);
                vocationadd.add(Vocation.KNIGHT);
                vocationadd.add(Vocation.BEAR);
                vocationadd.add(Vocation.DEFENDER);
                Collections.shuffle(vocationadd);
                if (VocationNumber.get(vocationadd.get(0)) == null) {
                    VocationNumber.put(vocationadd.get(0),1);
                    finded = true;
                }
            }
        }
        if (game.getPlayers().size() > 11) {
            VocationNumber.put(Vocation.WEREWOLF,4);
        }
        Main.CQ.sendGroupMsg(game.getGroup(),"正在发放身份,本场游戏共 " + game.getPlayers().size() + " 名玩家参与.");
        //Todo: 说明各角色数以及发角色
    }
}
