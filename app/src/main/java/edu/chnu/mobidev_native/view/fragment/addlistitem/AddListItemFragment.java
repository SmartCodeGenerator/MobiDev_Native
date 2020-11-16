package edu.chnu.mobidev_native.view.fragment.addlistitem;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import edu.chnu.mobidev_native.R;
import edu.chnu.mobidev_native.databinding.FragmentAddListItemBinding;
import edu.chnu.mobidev_native.view.fragment.listinfo.ListInfoFragment;
import edu.chnu.mobidev_native.viewmodel.listinfo.ListInfoViewModel;
import edu.chnu.mobidev_native.viewmodel.listinfo.factory.ListInfoViewModelFactory;
import edu.chnu.mobidev_native.viewmodel.util.SharedViewModel;

public class AddListItemFragment extends Fragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentAddListItemBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_list_item, container, false);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        ListInfoViewModelFactory factory = new ListInfoViewModelFactory(requireActivity()
                .getApplication());

        ListInfoViewModel viewModel = new ViewModelProvider(requireActivity(), factory)
                .get(ListInfoViewModel.class);

        viewModel.fetchData(model.getListId());

        binding.setListInfoViewModel(viewModel);

        binding.getRoot().findViewById(R.id.desc_label)
                .setOnClickListener(v -> focusOnEditor(v, binding.getRoot()));

        binding.getRoot().findViewById(R.id.price_label)
                .setOnClickListener(v -> focusOnEditor(v, binding.getRoot()));

        binding.getRoot().findViewById(R.id.edit_desc).setOnFocusChangeListener((v, hasFocus) -> {
            final InputMethodManager manager = (InputMethodManager)
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (hasFocus) {
                manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
            } else {
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        binding.getRoot().findViewById(R.id.edit_price).setOnFocusChangeListener((v, hasFocus) -> {
            final InputMethodManager manager = (InputMethodManager)
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (hasFocus) {
                manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
            } else {
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        binding.getRoot().findViewById(R.id.send_item_created_btn)
                .setOnClickListener(v -> sendNewPurchaseData(binding.getRoot()));

        return binding.getRoot();
    }

    private  void focusOnEditor(View view, View container) {
        if (view.getId() == R.id.desc_label) {
            container.findViewById(R.id.edit_desc).requestFocus();
        } else if (view.getId() == R.id.price_label) {
            container.findViewById(R.id.edit_price).requestFocus();
        }
    }

    private void sendNewPurchaseData(View container) {
        EditText descEdit = container.findViewById(R.id.edit_desc);
        EditText priceEdit = container.findViewById(R.id.edit_price);

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

            Bundle insert_response = new Bundle();
            insert_response.putString("itemDesc", descEdit.getText().toString().trim());
            insert_response
                    .putFloat("itemPrice", Float.parseFloat(priceEdit.getText().toString().trim()));

            getParentFragmentManager()
                    .setFragmentResult("insert_request", insert_response);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}