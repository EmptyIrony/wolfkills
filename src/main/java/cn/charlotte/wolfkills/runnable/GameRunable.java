    package cn.charlotte.wolfkills.runnable;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.manager.GameManager;

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
                    message.append("\r\n����������ÿ�˿���˵һ�仰����ͬ��");
                    Main.CQ.sendPrivateMsg(data.getQq(),message.toString());
                }
            });
            Main.getGameManager().wolfPrivate(game);

            Main.getGameManager().wolfVoting(game);






        }
}
}
