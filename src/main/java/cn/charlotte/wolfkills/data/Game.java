package cn.charlotte.wolfkills.data;

import cn.charlotte.wolfkills.enums.GameStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Game {
    public static Map<Long,Game> gameMap = new HashMap<>();

    private Long group;
    private List<PlayerData> players;
    private GameStatus status;
    private boolean end;
    private int nightNum;
    private List<PlayerData> wolfTeam;
    private List<PlayerData> humanTeam;

    public Game(){
        this.status = GameStatus.WAITING;
        this.players = new ArrayList<>();
        this.wolfTeam = new ArrayList<>();
        this.humanTeam = new ArrayList<>();

    }
}
