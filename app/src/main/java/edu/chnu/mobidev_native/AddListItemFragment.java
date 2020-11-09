package edu.chnu.mobidev_native;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import edu.chnu.mobidev_native.databinding.FragmentAddListItemBinding;
import edu.chnu.mobidev_native.viewmodels.SharedViewModel;

public class AddListItemFragment extends Fragment {

    private SharedViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentAddListItemBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_list_item, container, false);

        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding.setSharedViewModel(model);

        binding.getRoot().findViewById(R.id.desc_label).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                focusOnEditor(v, binding.getRoot());
            }
        });

        binding.getRoot().findViewById(R.id.price_label).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                focusOnEditor(v, binding.getRoot());
            }
        });

        binding.getRoot().findViewById(R.id.edit_desc).setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        binding.getRoot().findViewById(R.id.edit_price).setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        binding.getRoot().findViewById(R.id.send_item_created_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendNewPurchaseData(v, binding.getRoot());
            }
        });

        return binding.getRoot();
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

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}