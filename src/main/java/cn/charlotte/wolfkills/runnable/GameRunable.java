package cn.charlotte.wolfkills.runnable;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.manager.GameManager;
import lombok.Getter;

import static java.lang.Thread.sleep;

public class GameRunable implements Runnable {

    private int timer;
    private Game game;
    @Getter
    private GameManager gameManager;

    public GameRunable(Game game) {
        this.timer = 60;
        this.game = game;
        gameManager = Main.getGameManager();
        Main.getGameManager().setGame(game);
    }


    @Override
    public void run() {
        while (timer > 0) {
            if (game.getPlayers().size() < 7) {
                Main.CQ.sendGroupMsg(game.getGroup(), "�������㣬ֹͣ��ʱ��");
                return;
            }
            if (timer == 60 || timer == 30 || timer == 10 || timer == 5) {
                Main.CQ.sendGroupMsg(game.getGroup(), "��Ϸ���� " + timer + " ���ʼ��");
            }
            timer--;
            try {
                sleep(1000);
            } catch (Exception e) {
            }
        }
        //��ʼ��Ϸ�����
        Main.CQ.sendGroupMsg(game.getGroup(), "��Ϸ������ʼ�����ڽ��г�ʼ��...");
        game.setAlivePlayers(game.getPlayers());
        game.setStatus(GameStatus.STARTING);
        gameManager.sendVocations(game);
        try {
            sleep(3 * 1000);
        } catch (Exception ignored) {
        }

        Main.CQ.sendGroupMsg(game.getGroup(), "����Ʒ�����ϣ���Ϸ��ʼ��");

        while (!game.isEnd()) {
            game.setNightNum(game.getNightNum() + 1);
            Main.CQ.sendGroupMsg(game.getGroup(), "����ˣ������Ƕ������ж���");

            game.setStatus(GameStatus.WOLFTALK);
            game.getPlayers().values().forEach((data) -> {
                if (game.getWolfTeam().containsValue(data)) {
                    StringBuilder message = new StringBuilder();
                    message.append("������ã���Ķ�����\r\n");
                    game.getWolfTeam().values().forEach(playerData -> {
                        message.append(playerData.getNum() + "�� ");
                    });
                }
            });
            Thread thread = new Thread(gameManager);
            thread.start();
            while (!gameManager.isForceStop()) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Main.CQ.logInfo("[Debug]", "���һ��ѭ��");

        }
        StringBuilder message = new StringBuilder();
        message.append("��Ϸ������");
        if (game.getWinner().equalsIgnoreCase("wolf")) {
            message.append("���˻�ʤ��");
        } else {
            message.append("���˻�ʤ��");
        }

        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        Main.getMysql().settlement(game);
    }
}
