package cn.charlotte.wolfkills.listener;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.runnable.utils.Utils;

import java.util.Map;
import java.util.Set;

public class GameListener {
    public static void onPrivateMsg(int subType, int msgId, long fromQQ, String msg, int font){
        Set<Map.Entry<Long, cn.charlotte.wolfkills.data.Game>> entrySet = cn.charlotte.wolfkills.data.Game.gameMap.entrySet();
        for (Map.Entry<Long, cn.charlotte.wolfkills.data.Game> map:entrySet){
            cn.charlotte.wolfkills.data.Game game = map.getValue();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                PlayerData playerData = game.getPlayers().get(i);
                if(playerData.getQq() == fromQQ){
                    switch (game.getStatus()){
                        case DEFENDER:
                            if(playerData.isDead()){
                                if(playerData.getVocation().equals(Vocation.DEFENDER)){
                                    if(!Main.getGameManager().getTemp2()){
                                        if(Utils.isNum(msg)){
                                            int num = Integer.parseInt(msg);
                                            if(num > 0 && num <= game.getPlayers().size()){
                                                PlayerData player = Utils.getPlayerByNum(num, game);
                                                if(player == null){
                                                    //��Ȼ�Ҿ��ò�����ܻ���null
                                                    return;
                                                }
                                                if(Main.getGameManager().getTemp().equals(player)){
                                                    Main.getGameManager().setTemp(null);
                                                }
                                                Main.getGameManager().setTemp2(true);
                                                Main.CQ.sendPrivateMsg(fromQQ,"�㽫�ػ������: " + num + " ��");
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                            }
                                        } else {
                                            Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                        }
                                    }
                                }
                            }
                            break;
                        case WOLFTALK:
                            if(playerData.isDead()){
                                if(playerData.getVocation().equals(Vocation.WOLFKING)){
                                    if(Main.getGameManager().getWolfTalking().equals(playerData)){
                                        Main.getGameManager().sendWolfPrivateMessage(game,playerData,msg);
                                    } else {
                                        Main.CQ.sendPrivateMsg(fromQQ,"�����㲻��˵������������Ϊ���ڻ�û�ֵ�����Ѿ�˵����");
                                    }
                                }
                            }

                            break;
                        case WOLFSELECT:
                            if(playerData.isDead()){
                                if(playerData.getVocation().equals(Vocation.WOLFKING)){
                                    if(Utils.isNum(msg)){
                                        if(Main.getGameManager().getVoted().contains(fromQQ)){
                                            int num = Integer.parseInt(msg);
                                            if(num > 0 && num <= game.getPlayers().size()){
                                                PlayerData player = Utils.getPlayerByNum(num, game);
                                                if(player == null){
                                                    //��Ȼ�Ҿ��ò�����ܻ���null
                                                    return;
                                                }
                                                if(Main.getGameManager().getWolfVote().containsKey(player.getQq())){
                                                    Main.getGameManager().getWolfVote().put(player.getQq(), Main.getGameManager().getWolfVote().get(fromQQ) + 1);
                                                } else {
                                                    Main.getGameManager().getWolfVote().put(player.getQq(),1);
                                                }
                                                Main.getGameManager().getVoted().add(fromQQ);
                                                Main.CQ.sendPrivateMsg(fromQQ,"��Ͷ���� " + num + " ��");
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                            }
                                        }
                                    } else {
                                        Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                    }
                                }
                            }
                            break;
                        case WITCH:
                            if(playerData.isDead()){
                                if(playerData.getVocation().equals(Vocation.WITCH)){
                                    if(!Main.getGameManager().getTemp3()){
                                        if(Main.getGameManager().getTemp() != null && playerData.isPot1()){
                                            if(msg.split(" ")[0].equals("��")){
                                                String snum = msg.split(" ")[1];
                                                if(Utils.isNum(snum)){
                                                    int num = Integer.parseInt(snum);
                                                    if(num > 0 && num <= game.getPlayers().size()){
                                                        PlayerData player = Utils.getPlayerByNum(num, game);
                                                        if(player == null){
                                                            //��Ȼ�Ҿ��ò�����ܻ���null
                                                            return;
                                                        }
                                                        if(player.equals(Main.getGameManager().getTemp())){
                                                            Main.getGameManager().setTemp(null);
                                                        }
                                                        Main.getGameManager().setTemp3(true);
                                                    } else {
                                                        Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                                    }
                                                } else {
                                                    Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                                }
                                            }
                                            break;
                                        }
                                        if(playerData.isPot2()){
                                            if(msg.split(" ")[0].equals("��")){
                                                String snum = msg.split(" ")[1];
                                                if(Utils.isNum(snum)){
                                                    int num = Integer.parseInt(snum);
                                                    if(num > 0 && num <= game.getPlayers().size()){
                                                        PlayerData player = Utils.getPlayerByNum(num, game);
                                                        if(player == null){
                                                            //��Ȼ�Ҿ��ò�����ܻ���null
                                                            return;
                                                        }
                                                        if(!Main.getGameManager().getTemp().equals(player)){
                                                            Main.getGameManager().setTemp4(player);
                                                        }
                                                        Main.getGameManager().setTemp3(true);
                                                    } else {
                                                        Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                                    }
                                                } else {
                                                    Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            break;
                        case PROPHET:
                            if(playerData.isDead()){
                                if(playerData.getVocation().equals(Vocation.PROPHET)){
                                    if(!Main.getGameManager().getTemp5()){
                                        if(Utils.isNum(msg)){
                                            int num = Integer.parseInt(msg);
                                            if(num > 0 && num <= game.getPlayers().size()){
                                                PlayerData player = Utils.getPlayerByNum(num, game);
                                                if(player == null){
                                                    //��Ȼ�Ҿ��ò�����ܻ���null
                                                    return;
                                                }
                                                Main.getGameManager().setTemp5(true);
                                                if(game.getWolfTeam().contains(player)){
                                                    Main.CQ.sendPrivateMsg(fromQQ,num + " ��������: ����");
                                                } else {
                                                    Main.CQ.sendPrivateMsg(fromQQ,num + " ��������: ����");
                                                }
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                            }
                                        } else {
                                            Main.CQ.sendPrivateMsg(fromQQ,"������һ�������ĺ���");
                                        }
                                    }
                                }
                            }

                            break;
                        case PREPOLICE:
                            if(!playerData.isDead()){
                                if(msg.equals("�Ͼ�")){
                                    if(!Main.getGameManager().getPolice().contains(playerData)){
                                        Main.getGameManager().getPolice().add(playerData);
                                        Main.CQ.sendPrivateMsg(fromQQ,"û������ģʽ�ظ���д(�ɹ�)");
                                    } else {
                                        Main.CQ.sendPrivateMsg(fromQQ,"û������ģʽ�ظ���д(ʧ��)");
                                    }
                                }
                            }

                            break;
                        case POLICE:
                            if(!playerData.isDead()){
                                if(msg.equals("��")){
                                    //��ʱ�㿴����ô�����
                                }
                            }
                            break;
                        case QUITPOLICE:
                            if(!playerData.isDead()){
                                if(msg.equals("��ˮ")){
                                    if(Main.getGameManager().getPolice().contains(playerData)){
                                        Main.getGameManager().getPolice().remove(playerData);
                                        Main.CQ.sendPrivateMsg(fromQQ,"û������ģʽ�ظ���д(�ɹ�)");
                                    } else {
                                        Main.CQ.sendPrivateMsg(fromQQ,"û������ģʽ�ظ���д(ʧ��)");
                                    }
                                }
                            }
                            break;
                        case VOTEPOLICE:

                            break;
                        case VOTE:

                            break;
                    }
                    return;
                }
            }
        }
    }
}
