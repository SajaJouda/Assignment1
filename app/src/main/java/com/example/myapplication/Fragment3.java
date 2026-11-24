package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class Fragment3 extends Fragment implements CompoundButton.OnCheckedChangeListener {

    public interface OnConfirmListener {
        void onConfirmChanged(boolean checked);
    }

    private static final String ARG_NAME = "name";

    private Fragment3Binding binding;
    private OnConfirmListener listener;
    private String name;

    public Fragment3() {
    }

    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnConfirmListener) {
            listener = (OnConfirmListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);

        binding.textName.setText("Hello " + name);

        binding.buttonFinish.setEnabled(false);
        binding.buttonFinish.setText("Continue");

        binding.checkBoxConfirm.setOnCheckedChangeListener(this);

        binding.buttonFinish.setOnClickListener(v ->
                Toast.makeText(getContext(), "Done, welcome " + name, Toast.LENGTH_SHORT).show());

        return binding.getRoot();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            binding.buttonFinish.setEnabled(true);
            binding.buttonFinish.setText("Finish");
        } else {
            binding.buttonFinish.setEnabled(false);
            binding.buttonFinish.setText("Continue");
        }

        if (listener != null) {
            listener.onConfirmChanged(isChecked);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
