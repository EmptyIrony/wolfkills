package cn.charlotte.wolfkills.util.timer;

import cn.charlotte.wolfkills.util.timer.api.Timer;

import java.time.Duration;
import java.time.Instant;

public class TimerThread extends Thread {

    private TimerManager timerManager;

    public TimerThread(TimerManager timerManager) {
        this.timerManager = timerManager;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                doTimerLogic();
                sleep(timerManager.getTickTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doTimerLogic() {
        final Instant now = Instant.now();
        if (timerManager == null || now == null) {
            return;
        }
        if (timerManager.getBeginTime() == null) {
            timerManager.setBeginTime(now);
        }
        timerManager.setDeltaTime(Duration.between(timerManager.getBeginTime(), now));

        // Check if tick should be called.
        if (timerManager.getDeltaTime().getSeconds() > 0) {
            timerManager.getTimers().stream()
                    .filter(timer -> !timer.isPaused())
                    .forEach(Timer::tick);
            timerManager.setBeginTime(now);
        }

        // Check if timer is complete
        timerManager.getTimers().stream()
                .filter(timer -> !timer.isPaused())
                .filter(Timer::isComplete)
                .forEach(Timer::onComplete);
    }
}
