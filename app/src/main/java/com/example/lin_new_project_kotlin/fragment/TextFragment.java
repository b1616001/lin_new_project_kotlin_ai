package com.example.lin_new_project_kotlin.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project_kotlin.databinding.FragmentTextBinding;
import com.example.lin_new_project_kotlin.viewBinding.BaseBindingFragment;


public class TextFragment extends BaseBindingFragment<FragmentTextBinding> {

    @Override
    protected FragmentTextBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FragmentTextBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}