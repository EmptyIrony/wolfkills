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

    //�������
    public void sendVocations(Game game) {
        Main.CQ.setGroupWholeBan(game.getGroup(),true);
        //�ж���Ϸ����������������
        List<Vocation> vocations = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            vocations.add(Vocation.VILLAGER);
        }

        for (int i = 0; i < 2; i++) {
            vocations.add(Vocation.WOLF);
        }

        vocations.add(Vocation.PROPHET);
        vocations.add(Vocation.WITCH);
        //8������ʱ�������һ��δ�����Ľ�ɫ
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

        Main.CQ.sendGroupMsg(game.getGroup(),"���ڷ������,������Ϸ�� " + game.getPlayers().size() + " ����Ҳ���.");
        //Todo: ˵������ɫ���Լ�����ɫ
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData data = game.getPlayers().get(i);
            data.setNum(i+1);
            data.setVocation(vocations.get(i));

            switch (data.getVocation()) {
                case VILLAGER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�ƽ��\r\n����: ��Ĺ۲�����������ļ��ܣ��������˵Ļ��ԣ�����������Ӫ���ʤ����");
                    break;
                case WOLF:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ����ˡ�\r\n����: ÿ���ܹ�����һ����ң����ܳ������е������������ʤ����˽���ҡ��Ա��������Ա�������ֱ�ӽ�����һ�����");
                    break;
                case PROPHET:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�Ԥ�Լҡ�\r\n����: ÿ���ܹ���֤һ������Ǻ��˻��ǻ��ˣ���ú������Σ��������Ϣ������˻�ʤ��");
                    break;
                case HUNTER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ����ˡ�\r\n����: ��������Դ��߳�������һ�����ŵ���ң���Ҳ����ѡ�񲻿�ǹ");
                    break;
                case BEAR:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ��ܡ�\r\n����: ������������λ������λ������ʱ ������������û�л������ѳ����򲻻�����");
                    break;
                case WITCH:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�Ů�ס�\r\n����: ����һƿ��ҩ�Ͷ�ҩ����ҩ���Է�ֹһ�������ҹ����ȥ����ҩ���Զ���һ����ң��¶�������Ҳ����Է������ܻ��߱������ػ�");
                    break;
                case KNIGHT:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ���ʿ��\r\n����: ������ڰ�������ʱ�̶���˵���� ID�����ɶ�������Ծ�������������Ϊ��������������ݣ����ǳ��֣�ֱ�ӽ�����һ����ڣ�����Ǻ��ˣ���������л��");
                    break;
                case DEFENDER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�������\r\n����: �������ҹ���ػ�һ����ң�������Ҳ��ᱻ����ɱ�����㲻����������ͬʱ�ػ�һ�����");
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
            Main.CQ.sendPrivateMsg(playerData.getQq(),"�����ֵ��㷢���ˣ���ʱ15s��һ�仰");
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
                message.append(player.getNum()+". �����ˡ�"+Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
                continue;
            }

        if (game.getNightNum() == 1) {
            message.append(player.getNum() + ". ��ҹäɱ\r\n");
            continue;
        }

        message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
    }
       message.append("��ֱ�ӻظ���Ž���ͶƱ 20s");

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
        message.append("�뷢����Ҫ�ػ����˵���ţ���ע���㲻�������������ػ�ͬһ�����");
        
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
                message.append(player.getNum()+". ��ҹä��");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("����"+"������Ҫ��ɱ�ı���"+"�������� 20s"); //ȱ�ٱ���

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
                message.append(player.getNum()+". ��ҹä��");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("��Ҫ��֤��ݵ��ǣ���ֱ�ӻظ���� 20s");

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
        Main.CQ.sendGroupMsg(game.getGroup(), "�����ˣ����ڿ�ʼ������ѡ����\r\n��Ҫ�μӾ�ѡ����ҿ���˽�ķ��͸��ҡ��Ͼ��� 20s");
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
        message.append("�Ͼ��������\r\n");
        //ȱ���Ͼ�list

        message.append("���ڴ�"+""+"�ſ�ʼ����");

        Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
    }
    public void quitPolice(Game game){
        if (game.getStatus()!=GameStatus.QUITPOLICE){
            return;
        }

        StringBuilder message = new StringBuilder();
        message.append("������ϣ���Ҫ�˳���ѡ�������˽�Ļ����ˡ���ˮ�� 20s");
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
            message.append("���ھ��ϵ���һ�ʣ\r\n");
            message.append("����list");
            message.append("\r\n���λ���ͶƱ 20s");
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
        message.append("����"+""+"������");
        if (game.getNightNum()==1){
            message.append(""+"�ţ���˵���� 120s �������������롾����");
            Main.CQ.sendGroupMsg(game.getGroup(),message.toString());

        }
        StringBuilder msg = new StringBuilder();
        if (game.getPolice()!=null){
            game.setStatus(GameStatus.CHOSINGSAY);
            msg.append("������������ɾ����Ի��Ǿ��ҷ��� ˽���ҡ��󡿻��ߡ��ҡ� 20s");
            Main.CQ.sendGroupMsg(game.getGroup(),msg.toString());
            try {
                sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            PlayerData say = game.getAlivePlayers().get(new Random().nextInt(game.getAlivePlayers().size()));
            msg.append("û�о����������"+say.getNum()+"�ſ�ʼ����");
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
