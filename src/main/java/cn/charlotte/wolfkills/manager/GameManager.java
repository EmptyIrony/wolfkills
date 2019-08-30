package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.Vocation;

import java.util.*;

public class GameManager {

    //�������
    public static void sendVocations(Game game) {
        Main.CQ.setGroupWholeBan(game.getGroup(),true);
        //�ж���Ϸ����������������
        Map<Vocation,Integer> VocationNumber = new HashMap<>();
        VocationNumber.put(Vocation.VILLAGER,3);
        VocationNumber.put(Vocation.WEREWOLF,2);
        VocationNumber.put(Vocation.PROPHET,1);
        VocationNumber.put(Vocation.WITCH,1);
        //8������ʱ�������һ��δ�����Ľ�ɫ
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
        Main.CQ.sendGroupMsg(game.getGroup(),"���ڷ������,������Ϸ�� " + game.getPlayers().size() + " ����Ҳ���.");
        //Todo: ˵������ɫ���Լ�����ɫ
    }
}
