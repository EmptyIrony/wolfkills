package cn.charlotte.wolfkills.listener;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.util.StringUtils;

import java.util.Map;
import java.util.Set;

public class GameListener {


    public static void onPrivateMsg(int subType, int msgId, long fromQQ, String msg, int font) {
        Set<Map.Entry<Long, cn.charlotte.wolfkills.data.Game>> entrySet = cn.charlotte.wolfkills.data.Game.gameMap.entrySet();
        for (Map.Entry<Long, cn.charlotte.wolfkills.data.Game> map : entrySet) {
            cn.charlotte.wolfkills.data.Game game = map.getValue();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                PlayerData playerData = game.getPlayers().get(i);
                if (playerData.getQq() == fromQQ) {
                    switch (game.getStatus()) {
                        case DEFENDER:
                            if (playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.DEFENDER)) {
                                    if (!Main.getGameManager().getTemp2()) {
                                        if (StringUtils.isNum(msg)) {
                                            int num = Integer.parseInt(msg);
                                            if (num > 0 && num <= game.getPlayers().size()) {
                                                PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                if (player == null) {
                                                    //��Ȼ�Ҿ��ò�����ܻ���null
                                                    return;
                                                }
                                                if (Main.getGameManager().getTemp().equals(player)) {
                                                    Main.getGameManager().setTemp(null);
                                                }
                                                Main.getGameManager().setTemp2(true);
                                                Main.CQ.sendPrivateMsg(fromQQ, "�㽫�ػ������: " + num + " ��");
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                            }
                                        } else {
                                            Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                        }
                                    }
                                }
                            }
                            break;
                        case WOLFTALK:
                            if (playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.WOLFKING)) {
                                    if (Main.getGameManager().getWolfTalking().equals(playerData)) {
                                        Main.getGameManager().sendWolfPrivateMessage(game, playerData, msg);
                                    } else {
                                        Main.CQ.sendPrivateMsg(fromQQ, "�����㲻��˵������������Ϊ���ڻ�û�ֵ�����Ѿ�˵����");
                                    }
                                }
                            }

                            break;
                        case WOLFSELECT:
                            if (playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.WOLFKING)) {
                                    if (StringUtils.isNum(msg)) {
                                        if (Main.getGameManager().getVoted().contains(fromQQ)) {
                                            int num = Integer.parseInt(msg);
                                            if (num > 0 && num <= game.getPlayers().size()) {
                                                PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                if (player == null) {
                                                    //��Ȼ�Ҿ��ò�����ܻ���null
                                                    return;
                                                }
                                                if (Main.getGameManager().getWolfVote().containsKey(player.getQq())) {
                                                    Main.getGameManager().getWolfVote().put(player.getQq(), Main.getGameManager().getWolfVote().get(fromQQ) + 1);
                                                } else {
                                                    Main.getGameManager().getWolfVote().put(player.getQq(), 1);
                                                }
                                                Main.getGameManager().getVoted().add(fromQQ);
                                                Main.CQ.sendPrivateMsg(fromQQ, "��Ͷ���� " + num + " ��");
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                            }
                                        }
                                    } else {
                                        Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                    }
                                }
                            }
                            break;
                        case WITCH:
                            if (playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.WITCH)) {
                                    if (!Main.getGameManager().getTemp3()) {
                                        if (Main.getGameManager().getTemp() != null && playerData.isPot1()) {
                                            if (msg.split(" ")[0].equals("��")) {
                                                String snum = msg.split(" ")[1];
                                                if (StringUtils.isNum(snum)) {
                                                    int num = Integer.parseInt(snum);
                                                    if (num > 0 && num <= game.getPlayers().size()) {
                                                        PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                        if (player == null) {
                                                            //��Ȼ�Ҿ��ò�����ܻ���null
                                                            return;
                                                        }
                                                        if (player.equals(Main.getGameManager().getTemp())) {
                                                            Main.getGameManager().setTemp(null);
                                                        }
                                                        Main.getGameManager().setTemp3(true);
                                                    } else {
                                                        Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                                    }
                                                } else {
                                                    Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                                }
                                            }
                                            break;
                                        }
                                        if (playerData.isPot2()) {
                                            if (msg.split(" ")[0].equals("��")) {
                                                String snum = msg.split(" ")[1];
                                                if (StringUtils.isNum(snum)) {
                                                    int num = Integer.parseInt(snum);
                                                    if (num > 0 && num <= game.getPlayers().size()) {
                                                        PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                        if (player == null) {
                                                            //��Ȼ�Ҿ��ò�����ܻ���null
                                                            return;
                                                        }
                                                        if (!Main.getGameManager().getTemp().equals(player)) {
                                                            Main.getGameManager().setTemp4(player);
                                                        }
                                                        Main.getGameManager().setTemp3(true);
                                                    } else {
                                                        Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                                    }
                                                } else {
                                                    Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            break;
                        case PROPHET:
                            if (playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.PROPHET)) {
                                    if (!Main.getGameManager().getTemp5()) {
                                        if (StringUtils.isNum(msg)) {
                                            int num = Integer.parseInt(msg);
                                            if (num > 0 && num <= game.getPlayers().size()) {
                                                PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                if (player == null) {
                                                    //��Ȼ�Ҿ��ò�����ܻ���null
                                                    return;
                                                }
                                                Main.getGameManager().setTemp5(true);
                                                if (game.getWolfTeam().contains(player)) {
                                                    Main.CQ.sendPrivateMsg(fromQQ, num + " ��������: ����");
                                                } else {
                                                    Main.CQ.sendPrivateMsg(fromQQ, num + " ��������: ����");
                                                }
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                            }
                                        } else {
                                            Main.CQ.sendPrivateMsg(fromQQ, "������һ�������ĺ���");
                                        }
                                    }
                                }
                            }

                            break;
                        case PREPOLICE:
                            if (!playerData.isDead()) {
                                if (msg.equals("�Ͼ�")) {
                                    if (!Main.getGameManager().getPolice().contains(playerData)) {
                                        Main.getGameManager().getPolice().add(playerData);
                                        Main.CQ.sendPrivateMsg(fromQQ, "�ɹ�");
                                    }
                                }
                            }

                            break;
                        case POLICE:
                            if (!playerData.isDead()) {
                                if (msg.equals("��")) {
                                    Main.getGameManager().setSayEnd(true);
                                }
                                Main.CQ.sendGroupMsg(game.getGroup(), playerData.getNum() + "�����: " + msg);
                            }
                            break;
                        case QUITPOLICE:
                            if (!playerData.isDead()) {
                                if (msg.equals("��ˮ")) {
                                    if (Main.getGameManager().getPolice().contains(playerData)) {
                                        Main.getGameManager().getPolice().remove(playerData);
                                        Main.CQ.sendPrivateMsg(fromQQ, "�ɹ�");
                                    }
                                }
                            }
                            break;
                        case VOTEPOLICE:

                            if (!playerData.isDead()) {
                                if (!Main.getGameManager().getPolice().contains(playerData)) {
                                    if (StringUtils.isNum(msg)) {
                                        int num = Integer.parseInt(msg);
                                        if (Main.getGameManager().getVoted().contains(fromQQ)) {
                                            return;
                                        }
                                        if (game.getVote().get(StringUtils.getPlayerByNum(num, game)) != null) {
                                            game.getVote().put(StringUtils.getPlayerByNum(num, game), game.getVote().get(StringUtils.getPlayerByNum(num, game)) + 1);
                                            Main.CQ.sendGroupMsg(fromQQ, "�ɹ�");
                                            Main.getGameManager().getVoted().add(fromQQ);
                                        }
                                    }
                                }
                            }

                            break;
                        case VOTE:
                            if (!playerData.isDead()) {
                                if (StringUtils.isNum(msg)) {
                                    int num = Integer.parseInt(msg);
                                    if (Main.getGameManager().getVoted().contains(fromQQ)) {
                                        return;
                                    }
                                    game.getVote().put(playerData, num);
                                    Main.CQ.sendGroupMsg(fromQQ, "�ɹ�");
                                    Main.getGameManager().getVoted().add(fromQQ);
                                }
                            }


                            break;
                        case SAY:
                            if (!playerData.isDead()) {
                                if (msg.equals("��")) {
                                    Main.getGameManager().setSayEnd(true);
                                }
                                if (Main.getGameManager().getSaying().getNum() == playerData.getNum()) {
                                    Main.CQ.sendGroupMsg(game.getGroup(), playerData.getNum() + "�����: " + msg);
                                }
                            }
                            break;

                        case BIU:
                            if (Main.getGameManager().isTemp6()) {
                                return;
                            }
                            if (StringUtils.isNum(msg)) {
                                int num = Integer.parseInt(msg);
                                StringUtils.getPlayerByNum(num, game).setDead(true);
                                Main.CQ.sendGroupMsg(game.getGroup(), "���˴�����" + msg + "��");
                            }
                            break;
                        case DEATHSAY:

                            break;
                        case SHAREPOLICE:
                            if (game.getPolice().getQq() == fromQQ) {
                                if (StringUtils.isNum(msg)) {
                                    game.setPolice(StringUtils.getPlayerByNum(Integer.parseInt(msg), game));
                                } else {
                                    if (msg.equals("˺")) {
                                        game.setPolice(null);
                                    }
                                }
                            }
                            break;
                        case CHOSINGSAY:
                            if (game.getPolice().getQq() == fromQQ) {
                                if (msg.equals("��")) {
                                    int num = game.getPolice().getNum();
                                    if (num == 1) {
                                        num = game.getPlayers().size();
                                    } else {
                                        num = num - 1;
                                    }
                                    Main.getGameManager().setSaying(StringUtils.getPlayerByNum(num, game));
                                    Main.CQ.sendGroupMsg(game.getGroup(), "����ѡ������");
                                } else if (msg.equals("��")) {
                                    int num = game.getPolice().getNum();
                                    if (num == game.getPlayers().size()) {
                                        num = 1;
                                    } else {
                                        num = num + 1;
                                    }
                                    Main.getGameManager().setSaying(StringUtils.getPlayerByNum(num, game));
                                    Main.CQ.sendGroupMsg(game.getGroup(), "����ѡ���ҷ���");
                                }
                            }
                            break;
                    }
                    return;
                }
            }
        }
    }
}
