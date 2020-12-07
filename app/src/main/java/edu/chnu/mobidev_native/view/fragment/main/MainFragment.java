package edu.chnu.mobidev_native.view.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import edu.chnu.mobidev_native.R;
import edu.chnu.mobidev_native.databinding.FragmentMainBinding;
import edu.chnu.mobidev_native.util.shoppinglist.ShoppingListDiffCallback;
import edu.chnu.mobidev_native.util.shoppinglist.ShoppingListViewHolderOnClickListenerUtils;
import edu.chnu.mobidev_native.view.adapter.shoppinglist.ShoppingListAdapter;
import edu.chnu.mobidev_native.viewmodel.list.ListViewModel;
import edu.chnu.mobidev_native.viewmodel.list.factory.ListViewModelFactory;
import edu.chnu.mobidev_native.viewmodel.util.SharedViewModel;
import timber.log.Timber;

public class MainFragment extends Fragment {

    private ListViewModel listViewModel;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentMainBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_main, container, false);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        EditText newListEditText = binding.getRoot().findViewById(R.id.new_list_edit_text);

        newListEditText.setOnFocusChangeListener((v, hasFocus) -> {
            final InputMethodManager manager = (InputMethodManager)
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (hasFocus) {
                manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
            } else {
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        ListViewModelFactory listViewModelFactory = new ListViewModelFactory(requireActivity()
                .getApplication());

        Timber.i("called ViewModelProviders.of!");

        listViewModel = new ViewModelProvider(requireActivity(), listViewModelFactory)
                .get(ListViewModel.class);

        binding.setListViewModel(listViewModel);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Button addNewListBtn = binding.getRoot().findViewById(R.id.add_new_list_btn);
        addNewListBtn.setOnClickListener(v -> addList(binding.getRoot()));

        ShoppingListAdapter adapter = new ShoppingListAdapter(new ShoppingListDiffCallback(),
                new ShoppingListViewHolderOnClickListenerUtils(requireActivity(),
                        listViewModel, sharedViewModel));

        binding.shoppingListsList.setAdapter(adapter);

        listViewModel.getShoppingLists().observe(getViewLifecycleOwner(),
                shoppingLists -> {
                    if (shoppingLists != null) {
                        adapter.submitList(shoppingLists);
                    }
                });

        listViewModel.getListDeleted().observe(getViewLifecycleOwner(),
                listDeletedInfo -> {
                    if (listDeletedInfo.getValue()) {
                        listDeleted(listDeletedInfo.getKey(), binding.getRoot());
                        listViewModel.onListDeleted();
                    }
                });

        return binding.getRoot();
    }

    private void listDeleted(String listName, View snackBarView) {
        Vibrator buzzer = requireActivity().getSystemService(Vibrator.class);

        buzzer.vibrate(VibrationEffect.createWaveform(ListViewModel.BUZZ_PATTERN, -1));

        Snackbar.make(snackBarView, listName + " видалено", Snackbar.LENGTH_SHORT).show();
    }

    private void addList(View container) {
        final Context ctx =  getContext();
        Timber.i(Objects.requireNonNull(ctx).toString());

        final EditText newListEditText = container.findViewById(R.id.new_list_edit_text);

        if (newListEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(ctx, "Заповніть поле з назвою списку", Toast.LENGTH_SHORT)
                    .show();
            newListEditText.post(newListEditText::requestFocusFromTouch);
        } else {
            listViewModel.addList(newListEditText.getText().toString(),
                    false, LocalDateTime.now(ZoneId.of("Europe/Kiev")));

            newListEditText.setText("");

            newListEditText.clearFocus();
        }
    }
}