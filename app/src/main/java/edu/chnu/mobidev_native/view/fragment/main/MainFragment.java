package edu.chnu.mobidev_native.view.fragment.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.view.fragment.listinfo.ListInfoFragment;
import edu.chnu.mobidev_native.viewmodel.list.ListViewModel;
import edu.chnu.mobidev_native.viewmodel.list.factory.ListViewModelFactory;
import edu.chnu.mobidev_native.viewmodel.util.SharedViewModel;
import timber.log.Timber;

public class MainFragment extends Fragment {

    private ListViewModel listViewModel;
    private SharedViewModel sharedViewModel;

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

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Button addNewListBtn = binding.getRoot().findViewById(R.id.add_new_list_btn);
        addNewListBtn.setOnClickListener(v -> addList(binding.getRoot()));

        final LinearLayout listsList = binding.getRoot().findViewById(R.id.lists_list);

        listViewModel.getShoppingLists().observe(getViewLifecycleOwner(),
                shoppingLists -> {
                    listsList.removeAllViews();
                    for (ShoppingList data : shoppingLists) {
                        drawList(data, getContext(), listsList);
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

    private void drawList(final ShoppingList data, Context context, LinearLayout listsList) {
        final String listName = data.getListName();

        LinearLayout listItem = new LinearLayout(context);
        listItem.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams listItemParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        listItemParams.topMargin = 10;

        TextView dateCreatedText = new TextView(context);
        dateCreatedText.setText(data.getTimeCreated());
        dateCreatedText.setTextSize(20);

        LinearLayout.LayoutParams dateCreatedParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        dateCreatedParams.leftMargin = 10;

        listItem.addView(dateCreatedText, dateCreatedParams);

        RelativeLayout infoGroup = new RelativeLayout(context);
        infoGroup.setPadding(10, 0, 10, 0);

        final TextView listNameText = new TextView(context);
        listNameText.setTextSize(35);
        listNameText.setText(listName);
        if (data.isCompleted()) {
            listNameText.setTextColor(Color.GREEN);
        }

        infoGroup.addView(listNameText, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        CheckBox statusCheckBox = new CheckBox(context);
        statusCheckBox.setEnabled(false);
        statusCheckBox.setChecked(data.isCompleted());
        statusCheckBox.setTextSize(35);

        RelativeLayout.LayoutParams checkParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        checkParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        infoGroup.addView(statusCheckBox, checkParams);

        listItem.addView(infoGroup, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        RelativeLayout actionGroup = new RelativeLayout(context);
        actionGroup.setPadding(0,0,10,0);

        Button delBtn = new Button(context);
        delBtn.setId(Button.generateViewId());
        delBtn.setText("x");
        delBtn.setBackgroundColor(Color.RED);

        RelativeLayout.LayoutParams delBtnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        delBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        delBtn.setOnClickListener(v -> {
            listViewModel.deleteList(data);
        });

        actionGroup.addView(delBtn, delBtnParams);

        Button showBtn = new Button(context);
        showBtn.setText("переглянути");

        RelativeLayout.LayoutParams showBtnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        showBtnParams.rightMargin = 10;
        showBtnParams.addRule(RelativeLayout.START_OF, delBtn.getId());

        showBtn.setOnClickListener(v -> {
            ListInfoFragment fragment = new ListInfoFragment();

            sharedViewModel.setListId(data.getId());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        actionGroup.addView(showBtn, showBtnParams);

        listItem.addView(actionGroup, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        listsList.addView(listItem, listItemParams);
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