package edu.chnu.mobidev_native;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AddListItemFragment extends Fragment {

    private String listName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_list_item, container,
                false);

        ((TextView) view.findViewById(R.id.add_purchase_header)).setText(listName);

        view.findViewById(R.id.desc_label).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                focusOnEditor(v, view);
            }
        });

        view.findViewById(R.id.price_label).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                focusOnEditor(v, view);
            }
        });

        view.findViewById(R.id.edit_desc).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final InputMethodManager manager = (InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (hasFocus) {
                    manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        view.findViewById(R.id.edit_price).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final InputMethodManager manager = (InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (hasFocus) {
                    manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        view.findViewById(R.id.send_item_created_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendNewPurchaseData(v, view);
            }
        });

        return view;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    private  void focusOnEditor(View view, View container) {
        if (view.getId() == R.id.desc_label) {
            container.findViewById(R.id.edit_desc).requestFocus();
        } else if (view.getId() == R.id.price_label) {
            container.findViewById(R.id.edit_price).requestFocus();
        }
    }

    private void sendNewPurchaseData(View view, View container) {
        EditText descEdit = (EditText) container.findViewById(R.id.edit_desc);
        EditText priceEdit = (EditText) container.findViewById(R.id.edit_price);

        if (descEdit.getText().toString().trim().equals("") ||
                priceEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Заповніть інформацію про нову покупку",
                    Toast.LENGTH_SHORT).show();

            if (descEdit.getText().toString().equals("")) {
                container.findViewById(R.id.edit_desc).requestFocus();
            } else if (priceEdit.getText().toString().trim().equals("")) {
                container.findViewById(R.id.edit_price).requestFocus();
            }
        } else {
            ListInfoFragment fragment = new ListInfoFragment();
            fragment.setListName(listName);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}