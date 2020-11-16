package edu.chnu.mobidev_native.view.fragment.listinfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

import edu.chnu.mobidev_native.R;
import edu.chnu.mobidev_native.databinding.FragmentListInfoBinding;
import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.view.fragment.addlistitem.AddListItemFragment;
import edu.chnu.mobidev_native.viewmodel.listinfo.ListInfoViewModel;
import edu.chnu.mobidev_native.viewmodel.listinfo.factory.ListInfoViewModelFactory;
import edu.chnu.mobidev_native.viewmodel.util.SharedViewModel;
import timber.log.Timber;

public class ListInfoFragment extends Fragment {

    private ListInfoViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Timber.i("onCreate called");
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentListInfoBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_list_info, container, false);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        ListInfoViewModelFactory factory = new ListInfoViewModelFactory(requireActivity()
                .getApplication());

        viewModel = new ViewModelProvider(requireActivity(), factory).get(ListInfoViewModel.class);

        viewModel.fetchData(model.getListId());

        binding.setListInfoViewModel(viewModel);

        Button addListItemBtn = binding.getRoot().findViewById(R.id.add_list_item_btn);

        addListItemBtn.setOnClickListener(v -> {
            AddListItemFragment fragment = new AddListItemFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        viewModel.getListItems().observe(getViewLifecycleOwner(), listWithItems -> {
            LinearLayout container1 = binding.getRoot().findViewById(R.id.list_items);
            container1.removeAllViews();

            for (ListItem item : Objects
                    .requireNonNull(listWithItems.listItems)) {
                drawItem(container1, item);
            }

            ((TextView) binding.getRoot().findViewById(R.id.list_info_total))
                    .setText(viewModel.getCalculatedTotalPrice());
        });

        Timber.i("onCreateView called");

        getParentFragmentManager().setFragmentResultListener("insert_request",
                this, (requestKey, result) -> {
            String description = result.getString("itemDesc");
            float price = result.getFloat("itemPrice");

            viewModel.insertItem(new ListItem(description, price, false,
                    model.getListId()));

            Toast.makeText(getContext(),
                    description + " додано до списку покупок", Toast.LENGTH_LONG).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Timber.i("onAttach called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.i("onStart called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.i("onResume called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.i("onPause called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.i("onStop called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.i("onDestroyView called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.i("onDestroy called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Timber.i("onDetach called");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_info_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share_list_info) {
            StringBuilder info = new StringBuilder();

            viewModel.collectInfo(info);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, info.toString());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void drawItem(LinearLayout container, ListItem listItem) {
        Context ctx = getContext();
        RelativeLayout item = new RelativeLayout(ctx);
        item.setPadding(16,16,16,16);

        RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        CheckBox itemCheckBox = new CheckBox(ctx);
        itemCheckBox.setId(listItem.getId());
        itemCheckBox.setText(listItem.getDescription());
        itemCheckBox.setChecked(listItem.isChecked());
        if (listItem.isChecked()) {
            itemCheckBox.setTextColor(Color.GREEN);
        }
        itemCheckBox.setTextSize(35);

        itemCheckBox.setOnClickListener(v -> {
            listItem.setChecked(!listItem.isChecked());
            viewModel.updateItem(listItem);
        });

        RelativeLayout.LayoutParams itemCheckBoxParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        itemCheckBoxParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        item.addView(itemCheckBox, itemCheckBoxParams);

        TextView priceText = new TextView(ctx);
        String priceStr = String
                .format(Locale.getDefault(), "%.2f грн", listItem.getPrice());
        priceText.setText(priceStr);
        priceText.setTextSize(30);

        RelativeLayout.LayoutParams priceTextParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        priceTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        priceTextParams.addRule(RelativeLayout.ALIGN_BASELINE, itemCheckBox.getId());

        item.addView(priceText, priceTextParams);

        container.addView(item, itemParams);
    }
}