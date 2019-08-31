package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.runnable.GameRunable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerManager {
    @Getter
    private Map<Long, Thread> runnableMap = new HashMap<>();

    public void addPlayer(long qq, Game game) {
        if (game.getPlayers().size() >= 12) {
            Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " ������Ϸʧ�ܣ�����������");
            return;
        }

        game.getPlayers().keySet().stream().filter(id -> Objects.equals(id, qq))
                .forEach(id -> Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(id) + " ������Ϸʧ�ܣ����Ѿ��������ⳡ��Ϸ��"));

        game.getPlayers().put(qq, new PlayerData(qq));

        Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " ������Ϸ�ɹ���(" + game.getPlayers().size() + "/12)");
        if (game.getPlayers().size() == 7) {
            runnableMap.computeIfAbsent(game.getGroup(), thread -> {
                GameRunable gameRunable = new GameRunable(game);

                Thread gameThread = new Thread(gameRunable);
                gameThread.start();

                runnableMap.put(game.getGroup(), gameThread);
                return gameThread;
            });
        }
    }

    public void removePlayer(long qq, Game game) {
        PlayerData playerData = game.getPlayers().remove(qq);

        if (playerData == null) {
            Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " �뿪��Ϸʧ�ܣ��������ڱ�����Ϸ�У�");
            return;
        }

        Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " �뿪��Ϸ�ɹ���(" + game.getPlayers().size() + "/12)");

        if (game.getPlayers().isEmpty()) {
            runnableMap.remove(game.getGroup());
            //��ʱ��ſ���ɾ������Ϸ
        }
    }
}
