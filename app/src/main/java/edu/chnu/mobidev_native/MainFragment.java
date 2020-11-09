package edu.chnu.mobidev_native;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.renderscript.ScriptGroup;
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

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.LinkedList;

import edu.chnu.mobidev_native.models.ShoppingList;
import edu.chnu.mobidev_native.viewmodels.ListViewModel;
import edu.chnu.mobidev_native.viewmodels.SharedViewModel;
import timber.log.Timber;

public class MainFragment extends Fragment {

    private ListViewModel viewModel;
    private SharedViewModel model;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        EditText newListEditText = (EditText) view.findViewById(R.id.new_list_edit_text);

        newListEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final InputMethodManager manager = (InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (hasFocus) {
                    manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        Timber.i("called ViewModelProviders.of!");

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Button addNewListBtn = (Button) view.findViewById(R.id.add_new_list_btn);
        addNewListBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                addList(v, view);
            }
        });

        final LinearLayout listsList = (LinearLayout) view.findViewById(R.id.lists_list);

        viewModel.getShoppingLists().observe(getViewLifecycleOwner(),
                new Observer<LinkedList<ShoppingList>>() {
            @Override
            public void onChanged(LinkedList<ShoppingList> shoppingLists) {
                listsList.removeAllViews();
                for (ShoppingList data : shoppingLists) {
                    drawList(data, getContext(), listsList);
                }
            }
        });

        viewModel.getListDeleted().observe(getViewLifecycleOwner(),
                new Observer<AbstractMap.SimpleEntry<String, Boolean>>() {
            @Override
            public void onChanged(AbstractMap
                                          .SimpleEntry<String, Boolean> listDeletedInfo) {
                if (listDeletedInfo.getValue()) {
                    listDeleted(listDeletedInfo.getKey());
                    viewModel.onListDeleted();
                }
            }
        });

        newListEditText.setText("");

        return view;
    }

    private void listDeleted(String listName) {
        Vibrator buzzer = requireActivity().getSystemService(Vibrator.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buzzer.vibrate(VibrationEffect.createWaveform(ListViewModel.BUZZ_PATTERN, -1));
        } else {
            buzzer.vibrate(ListViewModel.BUZZ_PATTERN, -1);
        }

        Toast.makeText(getContext(), listName + " видалено", Toast.LENGTH_SHORT).show();
    }

    private void drawList(ShoppingList data, Context context, LinearLayout listsList) {
        final String listName = data.getListName();
        final Context ctx = context;

        LinearLayout listItem = new LinearLayout(ctx);
        listItem.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams listItemParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        listItemParams.topMargin = 10;

        TextView dateCreatedText = new TextView(ctx);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateCreatedText.setText(dtf.format(data.getTimeCreated()));
        dateCreatedText.setTextSize(20);

        LinearLayout.LayoutParams dateCreatedParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        dateCreatedParams.leftMargin = 10;

        listItem.addView(dateCreatedText, dateCreatedParams);

        RelativeLayout infoGroup = new RelativeLayout(ctx);
        infoGroup.setPadding(10, 0, 10, 0);

        final TextView listNameText = new TextView(ctx);
        listNameText.setTextSize(35);
        listNameText.setText(listName);

        infoGroup.addView(listNameText, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        CheckBox statusCheckBox = new CheckBox(ctx);
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

        RelativeLayout actionGroup = new RelativeLayout(ctx);
        actionGroup.setPadding(0,0,10,0);

        Button delBtn = new Button(ctx);
        delBtn.setId(Button.generateViewId());
        delBtn.setText("x");
        delBtn.setBackgroundColor(Color.RED);

        RelativeLayout.LayoutParams delBtnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        delBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getShoppingLists() != null) {
                    viewModel.removeList(listName);
                }
            }
        });

        actionGroup.addView(delBtn, delBtnParams);

        Button showBtn = new Button(ctx);
        showBtn.setText("переглянути");

        RelativeLayout.LayoutParams showBtnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        showBtnParams.rightMargin = 10;
        showBtnParams.addRule(RelativeLayout.START_OF, delBtn.getId());

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListInfoFragment fragment = new ListInfoFragment();

                model.setSelectedList(listName);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        actionGroup.addView(showBtn, showBtnParams);

        listItem.addView(actionGroup, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        listsList.addView(listItem, listItemParams);
    }

    private void addList(View view, View container) {
        final Context ctx =  getContext();
        Timber.i(ctx.toString());

        final EditText newListEditText = (EditText) container.findViewById(R.id.new_list_edit_text);

        if (newListEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(ctx, "Заповніть поле з назвою списку", Toast.LENGTH_SHORT)
                    .show();
            newListEditText.post(new Runnable() {
                @Override
                public void run() {
                    newListEditText.requestFocusFromTouch();
                }
            });
        } else {
            viewModel.addList(newListEditText.getText().toString(),
                    false, LocalDateTime.now(ZoneId.of("Europe/Kiev")));

            newListEditText.setText("");

            newListEditText.clearFocus();
        }
    }
}