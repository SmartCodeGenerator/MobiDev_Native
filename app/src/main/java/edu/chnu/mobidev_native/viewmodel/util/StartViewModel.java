package edu.chnu.mobidev_native.viewmodel.util;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import timber.log.Timber;

public class StartViewModel extends ViewModel implements LifecycleObserver {

    private final MutableLiveData<Boolean> isLocked;

    private final MutableLiveData<Integer> secondsCountFocused;
    private final MutableLiveData<Integer> secondsCountTotal;
    private final MutableLiveData<Handler> handler;
    private final MutableLiveData<Runnable> runnableFocused;
    private final MutableLiveData<Runnable> runnableTotal;

    public  StartViewModel() {
        isLocked = new MutableLiveData<>();
        isLocked.setValue(false);

        secondsCountFocused = new MutableLiveData<>();
        secondsCountFocused.setValue(0);

        secondsCountTotal = new MutableLiveData<>();
        secondsCountTotal.setValue(0);

        handler = new MutableLiveData<>();
        handler.setValue(new Handler());

        runnableFocused = new MutableLiveData<>();
        runnableTotal = new MutableLiveData<>();
    }

    public boolean getLocked() {
        return isLocked.getValue();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void lockActivity() {
        isLocked.setValue(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void startTimerTotal() {
        Runnable timer = new Runnable() {
            @Override
            public void run() {
                secondsCountTotal.setValue(secondsCountTotal.getValue() + 1);
                Timber.i("Timer (total) is at: %s", secondsCountTotal.getValue());
                Objects.requireNonNull(handler.getValue())
                        .postDelayed(runnableTotal.getValue(), 1000);
            }
        };

        runnableTotal.setValue(timer);
        Objects.requireNonNull(handler.getValue())
                .postDelayed(runnableTotal.getValue(), 1000);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void stopTimerTotal() {
        Objects.requireNonNull(handler.getValue()).removeCallbacks(runnableTotal.getValue());
        float focusPercentage = (float) secondsCountFocused.getValue() /
                secondsCountTotal.getValue() * 100;
        Timber.i("%d/%d секунд - час роботи додатку. %.2f%c - додаток був у фокусі.",
               secondsCountFocused.getValue(), secondsCountTotal.getValue(),
                focusPercentage, '%');
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startTimerFocused() {
        Runnable timer = new Runnable() {
            @Override
            public void run() {
                secondsCountFocused.setValue(secondsCountFocused.getValue() + 1);
                Timber.i("Timer (focused) is at: %s", secondsCountFocused.getValue());
                Objects.requireNonNull(handler.getValue())
                        .postDelayed(runnableFocused.getValue(), 1000);
            }
        };

        runnableFocused.setValue(timer);
        Objects.requireNonNull(handler.getValue())
                .postDelayed(runnableFocused.getValue(), 1000);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopTimerFocused() {
        Objects.requireNonNull(handler.getValue()).removeCallbacks(runnableFocused.getValue());
    }
}
