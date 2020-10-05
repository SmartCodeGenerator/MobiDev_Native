package edu.chnu.mobidev_native;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import edu.chnu.mobidev_native.models.Purchase;

public class AddListItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_item);

        findViewById(R.id.edit_desc).setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        findViewById(R.id.edit_price).setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        Intent intent  = getIntent();
        String listName = intent.getStringExtra("LIST_NAME");
        ((TextView) findViewById(R.id.add_purchase_header)).setText(listName);
    }

    public  void focusOnEditor(View view) {
        if (view.getId() == R.id.desc_label) {
            findViewById(R.id.edit_desc).requestFocus();
        } else if (view.getId() == R.id.price_label) {
            findViewById(R.id.edit_price).requestFocus();
        }
    }

    // TODO помилка при відправці з пустими полями для вводу
    public void sendNewPurchaseData(View view) {
        String purchaseDescription = ((TextView) findViewById(R.id.edit_desc)).getText().toString();
        Float purchasePrice = Float.parseFloat(((TextView) findViewById(R.id.edit_price)).getText()
                .toString());
        String listName = ((TextView) findViewById(R.id.add_purchase_header)).getText().toString();

        if (!purchaseDescription.isEmpty() && purchaseDescription != null
                && purchasePrice != null) {
            Intent intent = new Intent(this, ListInfoActivity.class);
            intent.putExtra("PURCHASE_DESC", purchaseDescription);
            intent.putExtra("PURCHASE_PRICE", purchasePrice);
            intent.putExtra("LIST_NAME", listName);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Заповніть інформацію про нову покупку",
                    Toast.LENGTH_SHORT).show();
            if (purchaseDescription.isEmpty() || purchaseDescription == null) {
                findViewById(R.id.edit_desc).requestFocus();
            } else {
                findViewById(R.id.edit_price).requestFocus();
            }
        }
    }

}