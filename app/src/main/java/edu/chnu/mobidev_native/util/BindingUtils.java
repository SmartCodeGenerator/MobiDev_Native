package edu.chnu.mobidev_native.util;

import android.graphics.Color;
import android.util.Base64;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

import edu.chnu.mobidev_native.R;
import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;

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

    @BindingAdapter("suggestionName")
    static void setSuggestionName(TextView textView, PurchaseSuggestion suggestion) {
        textView.setText(suggestion.getName());
    }

    @BindingAdapter("suggestionPrice")
    static void setSuggestionPrice(TextView textView, PurchaseSuggestion suggestion) {
        String priceStr = String
                .format(Locale.getDefault(), "%.2f грн", suggestion.getPrice());
        textView.setText(priceStr);
    }

    @BindingAdapter("suggestionImage")
    static void setSuggestionImage(ImageView imageView, PurchaseSuggestion suggestion) {
        byte[] imageBytes = Base64.decode(suggestion.getImageBytes(), Base64.DEFAULT);

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(imageBytes)
                .apply(new RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
                .into(imageView);
    }
}
