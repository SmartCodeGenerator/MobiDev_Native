package edu.chnu.mobidev_native;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText newListEditText = (EditText) findViewById(R.id.new_list_edit_text);

        newListEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final InputMethodManager manager = (InputMethodManager)
                        getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (hasFocus) {
                    manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        Button addNewListBtn = (Button) findViewById(R.id.add_new_list_btn);

        newListEditText.setText("list 1");
        addList(addNewListBtn);

        newListEditText.setText("list 2");
        addList(addNewListBtn);

        newListEditText.setText("list 3");
        addList(addNewListBtn);

        newListEditText.setText("");
    }

    public void addList(View view) {
        final Context ctx =  this;

        LinearLayout listsList = (LinearLayout) findViewById(R.id.lists_list);
        final EditText newListEditText = (EditText) findViewById(R.id.new_list_edit_text);

        if (newListEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Заповніть поле з назвою списку", Toast.LENGTH_SHORT)
                    .show();
            newListEditText.post(new Runnable() {
                @Override
                public void run() {
                    newListEditText.requestFocusFromTouch();
                }
            });
        } else {
            LinearLayout listItem = new LinearLayout(this);
            listItem.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams listItemParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            listItemParams.topMargin = 10;

            TextView dateCreatedText = new TextView(this);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
            dateCreatedText.setText(dtf.format(now));
            dateCreatedText.setTextSize(20);

            LinearLayout.LayoutParams dateCreatedParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            dateCreatedParams.leftMargin = 10;

            listItem.addView(dateCreatedText, dateCreatedParams);

            RelativeLayout infoGroup = new RelativeLayout(this);
            infoGroup.setPadding(10, 0, 10, 0);

            final TextView listNameText = new TextView(this);
            listNameText.setTextSize(35);
            listNameText.setText(newListEditText.getText().toString());

            infoGroup.addView(listNameText, new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));

            CheckBox statusCheckBox = new CheckBox(this);
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

            RelativeLayout actionGroup = new RelativeLayout(this);
            actionGroup.setPadding(0,0,10,0);

            Button delBtn = new Button(this);
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

            Button showBtn = new Button(this);
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

                    Intent intent = new Intent(ctx, ListInfoActivity.class);
                    intent.putExtra("LIST_NAME", listName);
                    startActivity(intent);
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