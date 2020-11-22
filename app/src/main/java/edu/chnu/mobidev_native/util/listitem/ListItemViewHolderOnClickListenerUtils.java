package edu.chnu.mobidev_native.util.listitem;

import android.view.View;

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
        };
    }
}
