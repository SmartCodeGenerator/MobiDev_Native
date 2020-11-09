package edu.chnu.mobidev_native.viewmodels;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import timber.log.Timber;

public class StartViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<Boolean> isLocked;

    private MutableLiveData<Integer> secondsCountFocused;
    private MutableLiveData<Integer> secondsCountTotal;
    private MutableLiveData<Handler> handler;
    private MutableLiveData<Runnable> runnableFocused;
    private MutableLiveData<Runnable> runnableTotal;

    public  StartViewModel(Lifecycle lifecycle) {
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

        //lifecycle.addObserver(this);
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
                handler.getValue().postDelayed(runnableTotal.getValue(), 1000);
            }
        };

        runnableTotal.setValue(timer);
        handler.getValue().postDelayed(runnableTotal.getValue(), 1000);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void stopTimerTotal() {
        handler.getValue().removeCallbacks(runnableTotal.getValue());
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
                handler.getValue().postDelayed(runnableFocused.getValue(), 1000);
            }
        };

        runnableFocused.setValue(timer);
        handler.getValue().postDelayed(runnableFocused.getValue(), 1000);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopTimerFocused() {
        handler.getValue().removeCallbacks(runnableFocused.getValue());
    }
}
