package edu.chnu.mobidev_native.view.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import edu.chnu.mobidev_native.view.fragment.about.AboutFragment;
import edu.chnu.mobidev_native.view.fragment.main.MainFragment;
import edu.chnu.mobidev_native.R;
import edu.chnu.mobidev_native.view.fragment.termsofuse.TermsOfUseFragment;
import edu.chnu.mobidev_native.viewmodel.util.StartViewModel;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.i("onCreate called");

        setContentView(R.layout.activity_main);

        StartViewModel viewModel = new ViewModelProvider(this).get(StartViewModel.class);

        if (!viewModel.getLocked()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new MainFragment())
                    .commit();
        }

        getLifecycle().addObserver(viewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
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
}