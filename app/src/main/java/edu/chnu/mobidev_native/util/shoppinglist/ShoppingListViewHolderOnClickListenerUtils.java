package edu.chnu.mobidev_native.util.shoppinglist;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import edu.chnu.mobidev_native.R;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.view.fragment.listinfo.ListInfoFragment;
import edu.chnu.mobidev_native.viewmodel.list.ListViewModel;
import edu.chnu.mobidev_native.viewmodel.util.SharedViewModel;

public class ShoppingListViewHolderOnClickListenerUtils {

    private final ListViewModel listViewModel;
    private final SharedViewModel sharedViewModel;

    private final FragmentActivity activity;

    public ShoppingListViewHolderOnClickListenerUtils(FragmentActivity activity,
                                                      ListViewModel listViewModel,
                                                      SharedViewModel sharedViewModel) {
        this.activity = activity;
        this.listViewModel = listViewModel;
        this.sharedViewModel = sharedViewModel;
    }

    public View.OnClickListener getDeleteButtonOnClickListener(ShoppingList list) {
        return v -> listViewModel.deleteList(list);
    }

    public View.OnClickListener getInfoButtonOnClickListener(ShoppingList list) {
        return v -> {
            sharedViewModel.setListId(list.getId());

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new ListInfoFragment())
                    .addToBackStack(null)
                    .commit();
        };
    }
}
