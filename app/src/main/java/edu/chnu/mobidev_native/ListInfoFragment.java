package edu.chnu.mobidev_native;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.LinkedList;
import java.util.Objects;

import edu.chnu.mobidev_native.databinding.FragmentListInfoBinding;
import edu.chnu.mobidev_native.models.ListItem;
import edu.chnu.mobidev_native.viewmodels.ListInfoViewModel;
import edu.chnu.mobidev_native.viewmodels.SharedViewModel;
import timber.log.Timber;

public class ListInfoFragment extends Fragment {

    private ListInfoViewModel viewModel;
    private SharedViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Timber.i("onCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentListInfoBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_list_info, container, false);

        viewModel = ViewModelProviders.of(this).get(ListInfoViewModel.class);
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding.setListInfoViewModel(viewModel);

        Button addListItemBtn = (Button) binding.getRoot().findViewById(R.id.add_list_item_btn);

        addListItemBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddListItemFragment fragment = new AddListItemFragment();

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewModel.getListItems().observe(getViewLifecycleOwner(),
                new Observer<LinkedList<ListItem>>() {
            @Override
            public void onChanged(LinkedList<ListItem> listItems) {
                LinearLayout container = binding.getRoot().findViewById(R.id.list_items);
                container.removeAllViews();

                for (ListItem item : Objects.requireNonNull(viewModel.getListItems().getValue())) {
                    drawItem(container, item.getUid(), item.getDescription(), item.getPrice(),
                            item.isChecked());
                }
            }
        });

        binding.setSharedViewModel(model);

        Timber.i("onCreateView called");

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Timber.i("onActivityCreated called");
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
        switch (item.getItemId()) {
            case R.id.share_list_info:

                StringBuilder info = new StringBuilder();

                viewModel.collectInfo(model.getSelectedList().getValue(), info);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, info.toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void drawItem(LinearLayout container, int itemId, String desc, float price,
                          boolean isChecked) {
        Context ctx = getContext();
        RelativeLayout item = new RelativeLayout(ctx);
        item.setPadding(16,16,16,16);

        RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        CheckBox itemCheckBox = new CheckBox(ctx);
        itemCheckBox.setId(itemId);
        itemCheckBox.setText(desc);
        itemCheckBox.setChecked(isChecked);
        if (isChecked) {
            itemCheckBox.setTextColor(Color.GREEN);
        }
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

        TextView priceText = new TextView(ctx);
        priceText.setText(price + " грн");
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

    private void checkItem(View view) {
        final CheckBox target = (CheckBox) view;
        boolean checked = target.isChecked();

        if (checked) {
            target.setTextColor(Color.GREEN);
            ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1))
                    .setTextColor(Color.GREEN);
            ListItem targetItem = viewModel.getById(target.getId());
            if (targetItem != null) {
                targetItem.setChecked(true);
            }
        } else {
            target.setTextColor(Color.BLACK);
            ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1))
                    .setTextColor(Color.GRAY);
            ListItem targetItem = viewModel.getById(target.getId());
            if (targetItem != null) {
                targetItem.setChecked(false);
            }
        }
    }

}