package cn.charlotte.wolfkills.runnable;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.GameStatus;

import java.util.Collections;

import static java.lang.Thread.sleep;

public class GameRunable implements Runnable {

    private int timer;
    private Game game;

    public GameRunable(Game game){
        this.timer = 60;
        this.game = game;
    }


    @Override
    public void run() {
        while (timer > 0){
            if(game.getPlayers().size() < 7){
                Main.CQ.sendGroupMsg(game.getGroup(),"�������㣬ֹͣ��ʱ��");
                return;
            }
            if(timer == 60 || timer == 30 || timer == 10 || timer == 5){
                Main.CQ.sendGroupMsg(game.getGroup(),"��Ϸ���� " + timer + " ���ʼ��");
            }
            timer--;
            try {
                sleep(1000);
            } catch (Exception e) {
            }
        }
        //��ʼ��Ϸ�����
        Main.CQ.sendGroupMsg(game.getGroup(),"��Ϸ������ʼ�����ڽ��г�ʼ��...");
        Collections.shuffle(game.getPlayers());
        game.setAlivePlayers(game.getPlayers());
        game.setStatus(GameStatus.STARTING);
        Main.getGameManager().sendVocations(game);
        try{
            sleep(3*1000);
        }catch (Exception ignored){}

        Main.CQ.sendGroupMsg(game.getGroup(),"����Ʒ�����ϣ���Ϸ��ʼ��");

        while (!game.isEnd()){
            game.setNightNum(game.getNightNum()+1);
            Main.CQ.sendGroupMsg(game.getGroup(),"����ˣ������Ƕ������ж���");

            game.setStatus(GameStatus.WOLFTALK);
            game.getPlayers().forEach((data)->{
                if (game.getWolfTeam().contains(data)){
                    StringBuilder message = new StringBuilder();
                    message.append("������ã���Ķ�����\r\n");
                    game.getWolfTeam().forEach(playerData -> {
                        message.append(playerData.getNum()+"�� ");
                    });
                }
            });
            Main.getGameManager().start(game);
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
