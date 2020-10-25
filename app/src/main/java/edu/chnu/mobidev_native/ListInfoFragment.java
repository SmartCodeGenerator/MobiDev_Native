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
import androidx.fragment.app.Fragment;

import java.util.LinkedList;
import java.util.function.Predicate;

import edu.chnu.mobidev_native.models.ListItem;
import timber.log.Timber;

public class ListInfoFragment extends Fragment {

    private String listName;
    private LinkedList<ListItem> listItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        listItems = new LinkedList<>();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Timber.i("onCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_info, container, false);

        Button addListItemBtn = (Button) view.findViewById(R.id.add_list_item_btn);

        addListItemBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddListItemFragment fragment = new AddListItemFragment();
                fragment.setListName(listName);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ((TextView) view.findViewById(R.id.list_info_header)).setText(listName);

        addItem(view, "item1", 40);
        addItem(view, "item2", 40);
        addItem(view, "item3", 40);
        addItem(view, "item4", 40);

        Timber.i("onCreateView called");

        return view;
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
                info.append(listName + ":\n\n");

                for(ListItem listItem : listItems) {
                    info.append(String.format("%10s: %10.3f " +
                            (listItem.isChecked() ? "\u2713" : "") + "\n",
                            listItem.getDescription(),
                            listItem.getPrice()));
                }

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

    public void setListName(String listName) {
        this.listName = listName;
    }

    private void addItem(View container, String desc, float price) {
        Context ctx = getContext();
        RelativeLayout item = new RelativeLayout(ctx);
        item.setPadding(16,16,16,16);

        RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        CheckBox itemCheckBox = new CheckBox(ctx);
        itemCheckBox.setId(CheckBox.generateViewId());
        itemCheckBox.setText(desc);
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

        ((LinearLayout) container.findViewById(R.id.list_items)).addView(item, itemParams);

        listItems.add(new ListItem(itemCheckBox.getId(), desc, price, false));
    }

    private void checkItem(View view) {
        final CheckBox target = (CheckBox) view;
        boolean checked = target.isChecked();

        if (checked) {
            target.setTextColor(Color.GREEN);
            ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1))
                    .setTextColor(Color.GREEN);
            ListItem targetItem = listItems.stream().filter(new Predicate<ListItem>() {
                @Override
                public boolean test(ListItem i) {
                    return i.getItemId() == target.getId();
                }
            }).findAny().orElse(null);
            if (targetItem != null) {
                targetItem.setChecked(true);
            }
        } else {
            target.setTextColor(Color.BLACK);
            ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1))
                    .setTextColor(Color.GRAY);
            ListItem targetItem = listItems.stream().filter(new Predicate<ListItem>() {
                @Override
                public boolean test(ListItem i) {
                    return i.getItemId() == target.getId();
                }
            }).findAny().orElse(null);
            if (targetItem != null) {
                targetItem.setChecked(false);
            }
        }
    }



}