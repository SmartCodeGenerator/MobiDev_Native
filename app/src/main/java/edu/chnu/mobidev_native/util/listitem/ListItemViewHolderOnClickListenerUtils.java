package edu.chnu.mobidev_native.util.listitem;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;

import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.viewmodel.listinfo.ListInfoViewModel;

public class ListItemViewHolderOnClickListenerUtils {

    private final ListInfoViewModel listInfoViewModel;

    public ListItemViewHolderOnClickListenerUtils(ListInfoViewModel listInfoViewModel) {
        this.listInfoViewModel = listInfoViewModel;
    }

    public View.OnClickListener getListItemDescriptionOnClickListener(ListItem item) {
        return v -> {
            item.setChecked(!item.isChecked());
            listInfoViewModel.updateItem(item);

            if (v != null) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    checkBox.setTextColor(Color.GREEN);
                } else {
                    checkBox.setTextColor(Color.BLACK);
                }
            }
        };
    }
}
