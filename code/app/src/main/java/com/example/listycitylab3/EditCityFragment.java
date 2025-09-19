// EditCityFragment.java
package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class EditCityFragment extends DialogFragment {

    public interface EditCityDialogListener {   // ← can be public
        void onCityEdited(int position, City updatedCity);
    }

    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POSITION = "arg_position";

    private EditCityDialogListener listener;

    public EditCityFragment() {} // required empty constructor

    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, (Serializable) city);  // City must implement Serializable
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText cityEt = view.findViewById(R.id.edit_text_city_text);
        EditText provEt = view.findViewById(R.id.edit_text_province_text);

        Bundle args = requireArguments();        // ← safer than getArguments()
        City city = (City) args.getSerializable(ARG_CITY);
        int position = args.getInt(ARG_POSITION, -1);

        if (city != null) {
            // Make sure these match your City getters
            cityEt.setText(city.getName());
            provEt.setText(city.getProvince());
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle("Edit City")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (d, w) -> {
                    String newCity = cityEt.getText().toString().trim();
                    String newProv = provEt.getText().toString().trim();
                    listener.onCityEdited(position, new City(newCity, newProv));
                })
                .create();
    }
}
