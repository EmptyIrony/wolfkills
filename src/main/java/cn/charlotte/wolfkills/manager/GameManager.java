package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.runnable.GameRunable;
import lombok.Getter;

import java.util.*;

import static java.lang.Thread.sleep;
@Getter
public class GameManager {
    private PlayerData wolfTalking;
    private PlayerData saying;

    //发送身份
    public void sendVocations(Game game) {
        Main.CQ.setGroupWholeBan(game.getGroup(),true);
        //判断游戏人数以填充身份数量
        List<Vocation> vocations = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            vocations.add(Vocation.VILLAGER);
        }

        for (int i = 0; i < 2; i++) {
            vocations.add(Vocation.WOLF);
        }

        vocations.add(Vocation.PROPHET);
        vocations.add(Vocation.WITCH);
        //8人以上时随机增加一名未被填充的角色
        if  (game.getPlayers().size() > 7) {
            List<Vocation> vocationadd = new ArrayList<>();
            List<Vocation> needAdd = new ArrayList<>();
            vocationadd.add(Vocation.HUNTER);
            vocationadd.add(Vocation.KNIGHT);
            vocationadd.add(Vocation.BEAR);
            vocationadd.add(Vocation.DEFENDER);
            for (Vocation vocation : vocationadd) {
                if (!vocations.contains(vocation)){
                    needAdd.add(vocation);
                }
            }

            vocations.add(needAdd.get(new Random().nextInt(needAdd.size())));
        }
        if (game.getPlayers().size() > 8) {
            vocations.add(Vocation.WOLF);
        }
        if (game.getPlayers().size() > 9) {
            vocations.add(Vocation.VILLAGER);
        }
        if  (game.getPlayers().size() > 10) {
            List<Vocation> vocationadd = new ArrayList<>();
            List<Vocation> needAdd = new ArrayList<>();
            vocationadd.add(Vocation.HUNTER);
            vocationadd.add(Vocation.KNIGHT);
            vocationadd.add(Vocation.BEAR);
            vocationadd.add(Vocation.DEFENDER);
            for (Vocation vocation : vocationadd) {
                if (!vocations.contains(vocation)){
                    needAdd.add(vocation);
                }
            }

            vocations.add(needAdd.get(new Random().nextInt(needAdd.size())));
        }
        if (game.getPlayers().size() > 11) {
            vocations.add(Vocation.WOLF);
        }
        Collections.shuffle(vocations);

        Main.CQ.sendGroupMsg(game.getGroup(),"正在发放身份,本场游戏共 " + game.getPlayers().size() + " 名玩家参与.");
        //Todo: 说明各角色数以及发角色
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData data = game.getPlayers().get(i);
            data.setNum(i+1);
            data.setVocation(vocations.get(i));

            switch (data.getVocation()) {
                case VILLAGER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【平民】\r\n技能: 你的观察能力就是你的技能，打破狼人的谎言，帮助好人阵营获得胜利！");
                    break;
                case WOLF:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【狼人】\r\n技能: 每晚能够击败一名玩家，击败场上所有的神或者民则获得胜利！私聊我【自爆】即可自爆而亡，直接进入下一轮天黑");
                    break;
                case PROPHET:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【预言家】\r\n技能: 每晚能够验证一名玩家是好人还是坏人，获得好人信任，用你的信息带领好人获胜！");
                    break;
                case HUNTER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【猎人】\r\n技能: 你死后可以带走场上任意一名活着的玩家，你也可以选择不开枪");
                    break;
                case BEAR:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【熊】\r\n技能: 早上如果你的上位或者下位有狼人时 你会咆哮，如果没有或者你已出局则不会咆哮");
                    break;
                case WITCH:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【女巫】\r\n技能: 你有一瓶解药和毒药，解药可以防止一名玩家在夜里死去，毒药可以毒死一名玩家，呗毒死的玩家不可以发动技能或者被守卫守护");
                    break;
                case KNIGHT:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【骑士】\r\n技能: 你可以在白天任意时刻对我说【刺 ID】即可对他发起对决，如果该名玩家为狼人则你亮明身份，该狼出局，直接进入下一轮天黑，如果是好人，则你以死谢罪");
                    break;
                case DEFENDER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【守卫】\r\n技能: 你可以在夜里守护一名玩家，该名玩家不会被狼人杀死，你不可以在两晚同时守护一名玩家");
                    break;
                case WOLFKING:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "NMSL");
            }
            try{
                sleep(3*1000);
            }catch (Exception ignored){}
        }
        game.getPlayers().forEach(playerData -> {
            if (playerData.getVocation()==Vocation.WOLF){
                game.getWolfTeam().add(playerData);
            }else {
                game.getHumanTeam().add(playerData);
            }
        });
    }

    public void wolfPrivate(Game game){
        if (game.getStatus()!=GameStatus.WOLFTALK){
            return;
        }

        game.getWolfTeam().forEach(playerData -> {
            wolfTalking = playerData;
            Main.CQ.sendPrivateMsg(playerData.getQq(),"现在轮到你发言了，限时15s，一句话");
            try {
                sleep(15*1000);
            } catch (InterruptedException ignored) {}
        });
        game.setStatus(GameStatus.WOLFSELECT);
    }

    public void wolfVoting(Game game) {
        if (game.getStatus()!=GameStatus.WOLFSELECT){
            return;
        }

        StringBuilder message = new StringBuilder();
        message.append("\r\n");
        for (PlayerData player : game.getPlayers()) {
            if (player.getVocation() == Vocation.WOLF) {
                message.append(player.getNum()+". 【狼人】"+Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
                continue;
            }

        if (game.getNightNum() == 1) {
            message.append(player.getNum() + ". 首夜盲杀\r\n");
            continue;
        }

        message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
    }
       message.append("请直接回复序号进行投票 20s");

        game.getWolfTeam().forEach(playerData -> {
            if (!playerData.isDead()) {
                Main.CQ.sendPrivateMsg(playerData.getQq(), message.toString());
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        game.setStatus(GameStatus.DEFENDER);
        defendChoosing(game);
    }

    private void defendChoosing(Game game){
        if (game.getStatus()!=GameStatus.DEFENDER){
            return;
        }

        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers()) {
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("请发送你要守护的人的序号，请注意你不可以连着两晚守护同一名玩家");
        
        for (PlayerData player : game.getPlayers()) {
            if (player.getVocation()==Vocation.DEFENDER&&!player.isDead()){
                Main.CQ.sendPrivateMsg(player.getQq(),message.toString());
                break;
            }
        }
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        game.setStatus(GameStatus.WITCH);
        witchChoosing(game);
    }

    private void witchChoosing(Game game){
        if (game.getStatus()!=GameStatus.WITCH){
            return;
        }

        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers()) {
            if (game.getNightNum()==1){
                message.append(player.getNum()+". 首夜盲毒");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("昨晚，"+"这里需要被杀的变量"+"遇害，你 20s"); //缺少变量

        for (PlayerData player : game.getPlayers()) {
            if (player.getVocation()==Vocation.WITCH&&!player.isDead()){
                Main.CQ.sendPrivateMsg(player.getQq(),message.toString());
                break;
            }
        }
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.PROPHET);
        prophetChoosing(game);
    }

    private void prophetChoosing(Game game){
        if (game.getStatus()!=GameStatus.PROPHET){
            return;
        }
        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers()) {
            if (game.getNightNum()==1){
                message.append(player.getNum()+". 首夜盲验");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("你要验证身份的是？请直接回复序号 20s");

        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (game.getNightNum()==1){
            game.setStatus(GameStatus.PREPOLICE);
            prePolice(game);
        }else {
            game.setStatus(GameStatus.MORNING);
        }
    }

    private void prePolice(Game game){
        Main.CQ.sendGroupMsg(game.getGroup(), "天亮了，现在开始警长竞选环节\r\n想要参加竞选的玩家可以私聊发送给我【上警】 20s");
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.POLICE);
        police(game);
    }
    public void police(Game game){
        StringBuilder message = new StringBuilder();
        message.append("上警的玩家有\r\n");
        //缺少上警list

        message.append("现在从"+""+"号开始发言");

        Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
    }
    public void quitPolice(Game game){
        if (game.getStatus()!=GameStatus.QUITPOLICE){
            return;
        }

        StringBuilder message = new StringBuilder();
        message.append("发言完毕，想要退出竞选的玩家请私聊机器人【退水】 20s");
        Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.VOTEPOLICE);
        policeVoting(game);
    }

    private void policeVoting(Game game){
        if (game.getStatus()!=GameStatus.VOTEPOLICE){
            StringBuilder message = new StringBuilder();
            message.append("现在警上的玩家还剩\r\n");
            message.append("警上list");
            message.append("\r\n请各位玩家投票 20s");
            Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
            try{
                sleep(20*1000);
            }catch (Exception e){
                e.printStackTrace();
            }

            game.setStatus(GameStatus.MORNING);
        }
    }
    private void morning(Game game){
        StringBuilder message = new StringBuilder();
        message.append("昨晚，"+""+"号遇害");
        if (game.getNightNum()==1){
            message.append(""+"号，请说遗言 120s 结束发言请输入【过】");
            Main.CQ.sendGroupMsg(game.getGroup(),message.toString());

        }
        StringBuilder msg = new StringBuilder();
        if (game.getPolice()!=null){
            game.setStatus(GameStatus.CHOSINGSAY);
            msg.append("警长，请决定由警左发言还是警右发言 私聊我【左】或者【右】 20s");
            Main.CQ.sendGroupMsg(game.getGroup(),msg.toString());
            try {
                sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            PlayerData say = game.getAlivePlayers().get(new Random().nextInt(game.getAlivePlayers().size()));
            msg.append("没有警长，随机从"+say.getNum()+"号开始发言");
            saying = say;
            Main.CQ.sendGroupMsg(game.getGroup(),msg.toString());
        }
        game.setStatus(GameStatus.SAY);
    }
    private void removePolice(Game game){


    }


    private boolean isEnd(Game game){
        int wolfs = 0;
        int villager = 0;
        int god = 0;

        for (PlayerData alivePlayer : game.getAlivePlayers()) {
            if (alivePlayer.getVocation()==Vocation.WOLF){
                wolfs++;
            }else if (alivePlayer.getVocation()==Vocation.VILLAGER){
                villager++;
            }else {
                god++;
            }

        }

        if (wolfs>=(god+villager)||villager==0||god==0){
            game.setWinner("wolf");
            return true;
        }else if (wolfs==0){
            game.setWinner("human");
            return true;
        }
        return false;
    }

}
