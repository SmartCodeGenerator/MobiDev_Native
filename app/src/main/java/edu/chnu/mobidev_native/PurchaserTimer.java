package edu.chnu.mobidev_native;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import timber.log.Timber;

public class PurchaserTimer implements LifecycleObserver {
    private int secondsCountFocused;
    private int secondsCountTotal;
    private Handler handler;
    private Runnable runnableFocused;
    private Runnable runnableTotal;

    public PurchaserTimer() {
        setSecondsCountFocused(0);
        setSecondsCountTotal(0);
        handler = new Handler();
    }

    public PurchaserTimer(Lifecycle lifecycle) {
        this();
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void startTimerTotal() {
        runnableTotal = new Runnable() {
            @Override
            public void run() {
                setSecondsCountTotal(getSecondsCountTotal() + 1);
                Timber.i("Timer is at: %s", getSecondsCountTotal());
                handler.postDelayed(runnableTotal, 1000);
            }
        };

        handler.postDelayed(runnableTotal, 1000);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void stopTimerTotal() {
        handler.removeCallbacks(runnableTotal);
        float focusPercentage = (float) getSecondsCountFocused() / getSecondsCountTotal() * 100;
        Timber.i("%d/%d секунд - час роботи додатку. %.2f%c - додаток був у фокусі.",
                getSecondsCountFocused(), getSecondsCountTotal(), focusPercentage, '%');
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void startTimerFocused() {
        runnableFocused = new Runnable() {
            @Override
            public void run() {
                setSecondsCountFocused(getSecondsCountFocused() + 1);
                Timber.i("Timer is at: %s", getSecondsCountFocused());
                handler.postDelayed(runnableFocused, 1000);
            }
        };

        handler.postDelayed(runnableFocused, 1000);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopTimerFocused() {
        handler.removeCallbacks(runnableFocused);
    }

    public int getSecondsCountFocused() {
        return secondsCountFocused;
    }

    public void setSecondsCountFocused(int secondsCountFocused) {
        this.secondsCountFocused = secondsCountFocused;
    }

    public int getSecondsCountTotal() {
        return secondsCountTotal;
    }

    public void setSecondsCountTotal(int secondsCountTotal) {
        this.secondsCountTotal = secondsCountTotal;
    }
}
