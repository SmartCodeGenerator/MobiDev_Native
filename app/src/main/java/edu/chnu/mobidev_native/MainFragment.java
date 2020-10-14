package edu.chnu.mobidev_native;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        Button addNewListBtn = (Button) view.findViewById(R.id.add_new_list_btn);
        addNewListBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                addList(v, view);
            }
        });


        newListEditText.setText("list 1");
        addList(addNewListBtn, view);

        newListEditText.setText("list 2");
        addList(addNewListBtn, view);

        newListEditText.setText("list 3");
        addList(addNewListBtn, view);

        newListEditText.setText("");

        return view;
    }


    private void addList(View view, View container) {
        final Context ctx =  getContext();

        LinearLayout listsList = (LinearLayout) container.findViewById(R.id.lists_list);
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
            LinearLayout listItem = new LinearLayout(ctx);
            listItem.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams listItemParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            listItemParams.topMargin = 10;

            TextView dateCreatedText = new TextView(ctx);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
            dateCreatedText.setText(dtf.format(now));
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
            listNameText.setText(newListEditText.getText().toString());

            infoGroup.addView(listNameText, new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));

            CheckBox statusCheckBox = new CheckBox(ctx);
            statusCheckBox.setEnabled(false);
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
                    LinearLayout cnt = (LinearLayout) v.getParent().getParent();
                    String listName = ((TextView) ((RelativeLayout) cnt.getChildAt(1))
                            .getChildAt(0)).getText().toString();

                    ((LinearLayout) v.getParent().getParent().getParent()).removeView(
                            (View) v.getParent().getParent()
                    );

                    Toast.makeText(ctx, listName + " видалено", Toast.LENGTH_SHORT).show();
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
                    String listName = ((TextView) ((RelativeLayout) ((LinearLayout) v.getParent()
                            .getParent()).getChildAt(1)).getChildAt(0)).getText()
                            .toString();

                    ListInfoFragment fragment = new ListInfoFragment();
                    fragment.setListName(listName);

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

            newListEditText.setText("");

            newListEditText.clearFocus();
        }
    }
}