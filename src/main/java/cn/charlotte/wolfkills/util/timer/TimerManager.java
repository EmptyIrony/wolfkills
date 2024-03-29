package cn.charlotte.wolfkills.util.timer;

import cn.charlotte.wolfkills.util.timer.api.Timer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@RequiredArgsConstructor
public class TimerManager {

    private final Set<Timer> timers = ConcurrentHashMap.newKeySet();
    private final TimerThread thread = new TimerThread(this);

    private Duration deltaTime = Duration.ZERO;
    private Instant beginTime = Instant.now();
    private int tickTime = 25;

    /**
     * add_timer
     * <p>
     * Adds a timer to the {@link TimerManager#timers} collection
     *
     * @param timer The timer to add
     */
    public void addTimer(Timer timer) {
        timers.add(timer);
    }

    /**
     * remove_timer
     * <p>
     * Removes a from to the {@link TimerManager#timers} collection
     *
     * @param timer The timer to remove
     */
    public void removeTimer(Timer timer) {
        timers.remove(timer);
    }

    /**
     * get_timer
     *
     * @param id The id of the timer to retrieve
     * @return timer object
     */
    public Timer getTimer(String id) {
        return getTimers().stream()
                .filter(timer -> timer.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public void cleanup() {
        //TODO decide if I should auto end the timers?
        timers.clear();
        this.thread.stop();
    }

}
