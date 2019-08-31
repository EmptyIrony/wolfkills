package cn.charlotte.wolfkills.data;

import cn.charlotte.wolfkills.enums.GameStatus;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Game {
    public static Map<Long, Game> gameMap = new HashMap<>();

    private Long group;
    private Map<Long, PlayerData> players;
    private Map<Long, PlayerData> alivePlayers;
    private GameStatus status;
    private boolean end;
    private int nightNum;
    private Map<Long, PlayerData> wolfTeam;
    private Map<Long, PlayerData> humanTeam;
    private PlayerData police;
    private String winner;
    private Map<PlayerData, Integer> vote;
    private List<Long> wolfSaid;

    public Game(long group) {
        this.status = GameStatus.WAITING;
        this.players = new ConcurrentHashMap<>();
        this.wolfTeam = new ConcurrentHashMap<>();
        this.humanTeam = new ConcurrentHashMap<>();
        this.vote = new ConcurrentHashMap<>();
        this.group = group;
        this.wolfSaid = new CopyOnWriteArrayList<>();

    }
}
