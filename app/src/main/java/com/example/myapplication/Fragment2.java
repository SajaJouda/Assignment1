package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

public class Fragment2 extends Fragment {

    public interface OnContinueListener {
        void onContinue(String name, String email, String gender, String age, String university);
    }

    private Fragment2Binding binding;
    private OnContinueListener listener;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnContinueListener) {
            listener = (OnContinueListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = Fragment2Binding.inflate(inflater, container, false);

        binding.buttonContinue2.setOnClickListener(v -> {
            String name = binding.editText.getText().toString().trim();
            String email = binding.editEmail.getText().toString().trim();
            String age = binding.editAge.getText().toString().trim();
            String university = binding.editUniversity.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(email) || !email.contains("@")) {
                Toast.makeText(getContext(), "Enter a valid email!", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedId = binding.radioGroupGender.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getContext(), "Choose gender!", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selected = binding.getRoot().findViewById(selectedId);
            String gender = selected.getText().toString();

            if (listener != null) {
                listener.onContinue(name, email, gender, age, university);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
