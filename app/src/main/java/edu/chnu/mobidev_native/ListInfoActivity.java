package edu.chnu.mobidev_native;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ListInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_info);

        Intent intent = getIntent();
        String listName = intent.getStringExtra("LIST_NAME");
        TextView header = (TextView) findViewById(R.id.list_info_header);
        header.setText(listName);

        // TODO подивитсь причину не отримання даних з форми додавання нової покупки
        intent = getIntent();
        if(intent.getStringArrayExtra("PURCHASE_DESC") != null &&
                intent.getStringArrayExtra("PURCHASE_PRICE") != null) {
            listName = intent.getStringExtra("LIST_NAME");
            header = (TextView) findViewById(R.id.list_info_header);
            header.setText(listName);

            RelativeLayout item = new RelativeLayout(this);
            item.setPadding(16,16,16,16);

            RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            CheckBox itemCheckBox = new CheckBox(this);
            itemCheckBox.setId(CheckBox.generateViewId());
            itemCheckBox.setText(intent.getStringArrayExtra("PURCHASE_DESC").toString());
            itemCheckBox.setTextSize(35);

            itemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkItem(v);
                }
            });

            RelativeLayout.LayoutParams itemCheckBoxParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            itemCheckBoxParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            item.addView(itemCheckBox, itemCheckBoxParams);

            TextView priceText = new TextView(this);
            priceText.setText(intent.getStringArrayExtra("PURCHASE_PRICE").toString()
                    + " грн");
            priceText.setTextSize(30);

            RelativeLayout.LayoutParams priceTextParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            priceTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            priceTextParams.addRule(RelativeLayout.ALIGN_BASELINE, itemCheckBox.getId());

            item.addView(priceText, priceTextParams);

            ((LinearLayout) findViewById(R.id.list_items)).addView(item, itemParams);
        } else {
            Toast.makeText(this, "failed to add new item", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkItem(View view) {
        CheckBox target = (CheckBox) view;
        boolean checked = target.isChecked();

        if (checked) {
            target.setTextColor(Color.GREEN);
            ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1))
                    .setTextColor(Color.GREEN);
        } else {
            target.setTextColor(Color.BLACK);
            ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1))
                    .setTextColor(Color.GRAY);
        }
    }

    public void addNewPurchase(View view) {
        String listName = ((TextView) findViewById(R.id.list_info_header)).getText().toString();
        Intent intent = new Intent(this, AddListItemActivity.class);
        intent.putExtra("LIST_NAME", listName);
        startActivity(intent);
    }

}