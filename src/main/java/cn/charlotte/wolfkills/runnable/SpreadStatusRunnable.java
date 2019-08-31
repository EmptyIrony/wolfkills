package cn.charlotte.wolfkills.runnable;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.Vocation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@RequiredArgsConstructor
public class SpreadStatusRunnable implements Runnable {
    private final Game game;
    private final List<Vocation> vocations;

    @Override
    @SneakyThrows
    public void run() {
        Main.CQ.sendGroupMsg(game.getGroup(), "���ڷ������,������Ϸ�� " + game.getPlayers().size() + " ����Ҳ���.");
        //Todo: ˵������ɫ���Լ�����ɫ

        int id = 0;

        for (PlayerData playerData : game.getPlayers().values()) {
            playerData.setNum(id + 1);
            playerData.setVocation(vocations.get(id));

            Vocation vocation = playerData.getVocation();

            if (vocation.equals(Vocation.WOLF)) {
                game.getWolfTeam().put(playerData.getQq(), playerData);
            } else {
                game.getHumanTeam().put(playerData.getQq(), playerData);
            }

            Main.CQ.sendPrivateMsg(playerData.getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������� ��" + vocation.getTitle() + "�� " + (vocation.getAbility().isEmpty() ? "" : "\r\n����: " + vocation.getAbility()));

            try {
                Thread.sleep(1000L * 3);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            id++;
        }
    }

}
