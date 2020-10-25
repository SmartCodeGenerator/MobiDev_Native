package edu.chnu.mobidev_native;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private PurchaserTimer purchaserTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.i("onCreate called");

        setContentView(R.layout.activity_main);

        purchaserTimer = new PurchaserTimer(this.getLifecycle());

        if (savedInstanceState != null) {
            int value1 = savedInstanceState.getInt("TIMER_TOTAL");
            int value2 = savedInstanceState.getInt("TIMER_FOCUS");

            purchaserTimer.setSecondsCountTotal(value1);
            purchaserTimer.setSecondsCountFocused(value2);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, new MainFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (id) {
            case R.id.main_page:
                transaction.replace(R.id.main_container, new MainFragment()).commit();
                return true;
            case R.id.about_page:
                transaction.replace(R.id.main_container, new AboutFragment()).commit();
                return true;
            case R.id.terms_of_use_page:
                transaction.replace(R.id.main_container, new TermsOfUseFragment()).commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.i("onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.i("onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.i("onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.i("onStop called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Timber.i("onRestart called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.i("onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("TIMER_TOTAL", purchaserTimer.getSecondsCountTotal());
        int value1 = outState.getInt("TIMER_TOTAL");
        Timber.i("onSaveInstanceState (TIMER_TOTAL): " + value1);
        outState.putInt("TIMER_FOCUS", purchaserTimer.getSecondsCountFocused());
        int value2 = outState.getInt("TIMER_FOCUS");
        Timber.i("onSaveInstanceState (TIMER_FOCUS): " + value2);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int value1 = savedInstanceState.getInt("TIMER_TOTAL");
        Timber.i("onRestoreInstanceState (TIMER_TOTAL): " + value1);
        int value2 = savedInstanceState.getInt("TIMER_FOCUS");
        Timber.i("onRestoreInstanceState (TIMER_FOCUS): " + value2);
    }
}