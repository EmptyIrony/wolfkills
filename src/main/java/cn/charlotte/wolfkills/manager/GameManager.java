package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static java.lang.Thread.sleep;
@Getter
public class GameManager {
    private PlayerData wolfTalking;
    @Setter
    private PlayerData saying;
    private List<PlayerData> said = new ArrayList<>();
    @Setter
    private boolean sayEnd;

    private List<Long> voted = new ArrayList<>(); //��ͶƱ����
    private Map<Long, Integer> wolfVote = new HashMap<>();
    private PlayerData wolfKilled; //8֪��ȡʲô���ְ��Ҹ�һ�� �����˻�ɱ�����
    private boolean defenderUsed; //�����Ƿ��������
    private boolean whichUsed; //Ů���Ƿ��������
    private PlayerData whichKilled; //Ů�׻�ɱ�����'
    private boolean temp5; //Ԥ�Լ��Ƿ��������
    private boolean temp6;//���˼����Ƿ��ù�
    private List<PlayerData> police = new ArrayList<>();
    private PlayerData voteByeBye;
    private boolean end;

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
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ�ƽ��\r\n����: ��Ĺ۲�����������ļ��ܣ��������˵Ļ��ԣ�����������Ӫ���ʤ����");
                    break;
                case WOLF:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ����ˡ�\r\n����: ÿ���ܹ�����һ����ң����ܳ������е������������ʤ����˽���ҡ��Ա��������Ա�������ֱ�ӽ�����һ�����");
                    break;
                case PROPHET:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ�Ԥ�Լҡ�\r\n����: ÿ���ܹ���֤һ������Ǻ��˻��ǻ��ˣ���ú������Σ��������Ϣ������˻�ʤ��");
                    break;
                case HUNTER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ����ˡ�\r\n����: ��������Դ��߳�������һ�����ŵ���ң���Ҳ����ѡ�񲻿�ǹ");
                    break;
                case BEAR:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ��ܡ�\r\n����: ������������λ������λ������ʱ ������������û�л������ѳ����򲻻�����");
                    break;
                case WITCH:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ�Ů�ס�\r\n����: ����һƿ��ҩ�Ͷ�ҩ����ҩ���Է�ֹһ�������ҹ����ȥ����ҩ���Զ���һ����ң�����������Ҳ����Է������ܻ��߱������ػ�");
                    break;
                case KNIGHT:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ���ʿ��\r\n����: ������ڰ�������ʱ�̶���˵���� ID�����ɶ�������Ծ�������������Ϊ��������������ݣ����ǳ��֣�ֱ�ӽ�����һ����ڣ�����Ǻ��ˣ���������л��");
                    break;
                case DEFENDER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ  ���ǡ�" + data.getNum() + "����\r\n�������ǡ�������\r\n����: �������ҹ���ػ�һ����ң�������Ҳ��ᱻ����ɱ�����㲻����������ͬʱ�ػ�һ�����");
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

    public void start(Game game) {
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
        wolfVoting(game);
    }

    private void wolfVoting(Game game) {
        if (game.getStatus()!=GameStatus.WOLFSELECT){
            return;
        }
        for (PlayerData data : game.getPlayers()) {
            wolfVote.put(data.getQq(), 0);
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

        wolfKilled = voteByeByePlayer(game);

        game.setStatus(GameStatus.DEFENDER);
        defendChoosing(game);
        voted.clear();
    }

    private void defendChoosing(Game game){
        if (game.getStatus()!=GameStatus.DEFENDER){
            return;
        }
        Main.CQ.sendGroupMsg(game.getGroup(), "��������ѡ���ػ�Ŀ��...");

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
        Main.CQ.sendGroupMsg(game.getGroup(), "Ů�������ж�...");

        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers()) {
            if (game.getNightNum()==1){
                message.append(player.getNum()+". ��ҹä��");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("����" + (wolfKilled == null ? "��ƽ��ҹ" : wolfKilled.getNum() + "����������Է��͡��ȡ� 20s")); //ȱ�ٱ���

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
        Main.CQ.sendGroupMsg(game.getGroup(), "Ԥ�Լ���������...");
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData player = StringUtils.getPlayerByNum((i + 1), game);
            if (game.getNightNum()==1){
                message.append(player.getNum()+". ��ҹä��");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("��Ҫ��֤��ݵ��ǣ���ֱ�ӻظ���� 20s");
        for (PlayerData alivePlayer : game.getAlivePlayers()) {
            if (alivePlayer.getVocation() == Vocation.PROPHET) {
                Main.CQ.sendPrivateMsg(alivePlayer.getQq(), message.toString());
                break;
            }
        }

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
            morning(game);
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

    private void police(Game game) {
        StringBuilder message = new StringBuilder();
        message.append("�Ͼ��������\r\n");
        for (PlayerData data : police) {
            message.append(data.getNum() + "��");
        }
        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        try {
            sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (PlayerData data : police) {
            Main.CQ.sendGroupMsg(game.getGroup(), "" + data.getNum() + "�ſ�ʼ���� 120s");
            saying = data;
            int timer = 0;
            while (timer <= 120 && !sayEnd) {
                timer--;
                try {
                    sleep(1000);
                } catch (Exception ignored) {
                }
            }
            sayEnd = false;
        }

        game.setStatus(GameStatus.QUITPOLICE);
        quitPolice(game);

        Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
    }

    private void quitPolice(Game game) {
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
        for (PlayerData data : getPolice()) {
            game.getVote().put(data, 0);
        }

        policeVoting(game);
    }

    private void policeVoting(Game game){
        Main.CQ.logInfo("[Debug]", "ִ����policeVoting����");
        if (game.getStatus()!=GameStatus.VOTEPOLICE){
            return;
        }
        game.getVote().clear();
        for (PlayerData data : police) {
            game.getVote().put(data, 0);
        }

            StringBuilder message = new StringBuilder();
            message.append("���ھ��ϵ���һ�ʣ\r\n");
        for (PlayerData data : police) {
            message.append(data.getNum() + "�� ");
        }
            message.append("\r\n���λ���ͶƱ 20s");
            Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
            try{
                sleep(20*1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            int num = 0;
            List<PlayerData> data = new ArrayList<>();
            for (Map.Entry<PlayerData, Integer> entry : game.getVote().entrySet()) {
                if (data.isEmpty()) {
                    num = entry.getValue();
                    data.add(entry.getKey());
                }
                if (entry.getValue() > num) {
                    data.clear();
                    data.add(entry.getKey());
                    num = entry.getValue();
                }
                if (entry.getValue() == num) {
                    if (data.contains(entry.getKey())) {
                        continue;
                    }
                    data.add(entry.getKey());
                    num = entry.getValue();
                }
            }

            if (data.size() >= 2) {
                Main.CQ.sendGroupMsg(game.getGroup(), "ƽƱ������ڶ��ַ���");
                game.setStatus(GameStatus.POLICE);
                police = data;
                this.police(game);
            } else {
                Main.CQ.sendGroupMsg(game.getGroup(), data.get(0).getNum() + "����ҵ�ѡ����������1.5Ʊ��ʹȨ");
                game.setPolice(data.get(0));


                game.setStatus(GameStatus.MORNING);
                voted.clear();
                morning(game);
            }
    }
    private void morning(Game game){
        StringBuilder message = new StringBuilder();
        if (wolfKilled == null && whichKilled == null) {
            message.append("������ƽ��ҹ��û�������ȥ");
        } else if (wolfKilled == null || whichKilled == null) {
            message.append(wolfKilled == null ? "����" + whichKilled.getNum() + "�������ȥ" : "����" + wolfKilled.getNum() + "�������ȥ");
            if (wolfKilled == null) {
                useGun(game, whichKilled);
                whichKilled.setDead(true);
            } else {
                useGun(game, wolfKilled);
                wolfKilled.setDead(true);
            }
        } else {
            if (wolfKilled.getNum() > whichKilled.getNum()) {
                useGun(game, wolfKilled);
                useGun(game, whichKilled);
                wolfKilled.setDead(true);
                whichKilled.setDead(true);

                message.append("����" + whichKilled.getNum() + "�� " + wolfKilled.getNum() + "�� �������");
            } else {
                useGun(game, wolfKilled);
                useGun(game, whichKilled);
                message.append("����" + wolfKilled.getNum() + "�� " + whichKilled.getNum() + "�� �������");
            }
        }
        if (isEnd(game)) {
            game.setEnd(true);
        } else {
            game.setEnd(false);
        }

        if (isEnd(game)) {
            game.setEnd(true);
        } else {
            game.setEnd(false);
        }
        removePolice(game);
        if (game.getNightNum()==1){
            message.append(""+"�ţ���˵���� 120s �������������롾����");
            game.setStatus(GameStatus.DEATHSAY);
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
        say(game);
    }

    private void removePolice(Game game){
        if ((wolfKilled != null && game.getPolice().getNum() == wolfKilled.getNum()) || (whichKilled != null && game.getPolice().getNum() == whichKilled.getNum())) {
            game.setStatus(GameStatus.SHAREPOLICE);
            Main.CQ.sendGroupMsg(game.getGroup(), "��˽�ķ��͸�������Ҫ�����յ���ұ�ţ����߷��͡�˺����ȥ���� 20s");
            try {
                sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void useGun(Game game, PlayerData data) {
        if (data.getVocation() == Vocation.HUNTER) {
            game.setStatus(GameStatus.BIU);
            Main.CQ.sendGroupMsg(game.getGroup(), "���ˣ���ѡ���Ƿ�ǹ���������˽�ġ�ǹ ID�� 20s");
            try {
                sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    private void say(Game game) {
        if (game.getStatus() != GameStatus.SAY) {
            return;
        }
        while (said.size() <= game.getAlivePlayers().size()) {
            Main.CQ.sendGroupMsg(game.getGroup(), saying.getNum() + "������뷢�� 120s �������������롾����");
            int timer = 0;
            while (sayEnd || timer <= 120) {
                timer++;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            said.add(saying);
            sayEnd = false;

            int num = saying.getNum();
            num = num + 1;
            if (num > game.getPlayers().size()) {
                num = 1;
            }
            while (!game.getPlayers().get(num).isDead()) {
                num = num + 1;
                if (num > game.getPlayers().size()) {
                    num = 1;
                }
            }

            saying = game.getPlayers().get(num);
        }
        game.setStatus(GameStatus.VOTE);
        vote(game);
        game.getVote().clear();
        for (PlayerData alivePlayer : game.getAlivePlayers()) {
            game.getVote().put(alivePlayer, 0);
        }
    }

    private void vote(Game game) {
        StringBuilder message = new StringBuilder();

        message.append("������ҷ�����ϣ�������ͶƱʱ��\r\n");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData player = StringUtils.getPlayerByNum((i + 1), game);
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("��˽������Ҫ��ͶƱ 20s");
        try {
            sleep(20 * 1000);
        } catch (Exception ignored) {
        }

        int num = 0;
        List<PlayerData> data = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        StringBuilder vote = new StringBuilder();

        for (Map.Entry<PlayerData, Integer> entry : game.getVote().entrySet()) {
            vote.append(entry.getKey() + "�� -> " + entry.getValue());
            nums.add(entry.getValue());
        }
        List<Integer> integers = new ArrayList<>();
        int iii = 0;
        for (Integer integer : nums) {
            if (iii == 0) {
                iii = integer;
            }
            if (integer > iii) {
                integers.clear();
                integers.add(integer);
                iii = integer;
            }
            if (integer == iii) {
                if (integers.contains(integer)) {
                    continue;
                }
                integers.add(integer);
                iii = integer;
            }
        }
        Main.CQ.sendGroupMsg(game.getGroup(), vote.toString());

        if (integers.size() >= 2) {
            Main.CQ.sendGroupMsg(game.getGroup(), "ƽƱ������ڶ��ַ���");
            game.setStatus(GameStatus.SAY);
            this.say(game);
            return;
        }
        if (integers.isEmpty()) {
            Main.CQ.sendGroupMsg(game.getGroup(), "����ͶƱ�����˳���");
        } else {
            Main.CQ.sendGroupMsg(game.getGroup(), integers.get(0) + "�ű���Ͷ����");
            this.voteByeBye = StringUtils.getPlayerByNum(integers.get(0), game);
        }

        voteEnd(game);
        voted.clear();
        game.getVote().clear();
    }

    private void voteEnd(Game game) {
        StringBuilder message = new StringBuilder();
        message.append(voteByeBye.getNum() + "�ű���Ͷ����\r\n�� ��˵���� 120�� ����ʹ�á�����");
        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        game.setStatus(GameStatus.DEATHSAY);
        useGun(game, voteByeBye);
        removePolice(game);
        if (isEnd(game)) {
            game.setEnd(true);
            return;
        } else {
            game.setEnd(false);
        }
        int timer = 0;
        while (sayEnd || timer <= 120) {
            timer++;
            try {
                sleep(120 * 1000);
            } catch (Exception ignored) {
            }
        }
        sayEnd = false;
        end = true;

    }

    public PlayerData getWolfTalking() {
        return wolfTalking;
    }

    public Map<Long, Integer> getWolfVote() {
        return wolfVote;
    }

    public List<Long> getVoted() {
        return voted;
    }


    public PlayerData getWolfKilled() {
        return wolfKilled;
    }

    public void setWolfKilled(PlayerData wolfKilled) {
        this.wolfKilled = wolfKilled;
    }

    public boolean getDefenderUsed() {
        return defenderUsed;
    }

    public void setDefenderUsed(boolean defenderUsed) {
        this.defenderUsed = defenderUsed;
    }

    public boolean getWhichUsed() {
        return whichUsed;
    }

    public void setWhichUsed(boolean whichUsed) {
        this.whichUsed = whichUsed;
    }

    public PlayerData getWhichKilled() {
        return whichKilled;
    }

    public void setWhichKilled(PlayerData whichKilled) {
        this.whichKilled = whichKilled;
    }

    public boolean getTemp5() {
        return temp5;
    }

    public void setTemp5(boolean temp5) {
        this.temp5 = temp5;
    }

    public List<PlayerData> getPolice() {
        return police;
    }

    private PlayerData voteByeByePlayer(Game game) { //8֪��ȡʲô���ְ��Ҹ�һ��
        PlayerData temp = null;
        for (PlayerData playerData : game.getPlayers()) {
            if (wolfVote.containsKey(playerData.getQq())) {
                if (temp == null) {
                    temp = playerData;
                } else {
                    if (wolfVote.get(playerData.getQq()) > wolfVote.get(temp.getQq())) {
                        temp = playerData;
                    }
                }
            }
        }
        return temp;
    }

    public void sendWolfPrivateMessage(Game game, PlayerData sender, String msg) {
        for (PlayerData wolf : game.getWolfTeam()) {
            if (wolf.getNum() != sender.getNum()) {
                Main.CQ.sendPrivateMsg(wolf.getQq(), sender.getNum() + "�����: " + msg);
            }
        }
    }

    public void boom(Game game, PlayerData data) {
        data.setDead(true);
        if (isEnd(game)) {
            game.setEnd(true);
            return;
        } else {
            game.setEnd(false);
        }

        Main.CQ.sendGroupMsg(game.getGroup(), data.getNum() + "������Ա���ֱ�ӽ�����һ�����");
        game.getAlivePlayers().remove(data);


    }

}
