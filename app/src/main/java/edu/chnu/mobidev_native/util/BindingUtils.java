package edu.chnu.mobidev_native.util;

import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.util.Locale;

import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;

public interface BindingUtils {

    @BindingAdapter("timeListCreated")
    static void setTimeListCreated(TextView textView, ShoppingList list) {
        textView.setText(list.getTimeCreated());
    }

    @BindingAdapter("listName")
    static void  setListName(TextView textView, ShoppingList list) {
        textView.setText(list.getListName());
        if (list.isCompleted()) {
            textView.setTextColor(Color.GREEN);
        }
    }

    @BindingAdapter("listCompleted")
    static void setListCompleted(CheckBox checkBox, ShoppingList list) {
        checkBox.setChecked(list.isCompleted());
    }

    @BindingAdapter("listItemDescription")
    static void setListItemDescription(CheckBox checkBox, ListItem item) {
        checkBox.setText(item.getDescription());
        checkBox.setChecked(item.isChecked());
        if (item.isChecked()) {
            checkBox.setTextColor(Color.GREEN);
        }
    }

    @BindingAdapter("listItemPrice")
    static void setListItemPrice(TextView textView, ListItem item) {
        String priceStr = String.format(Locale.getDefault(), "%.2f грн", item.getPrice());
        textView.setText(priceStr);
    }
}
