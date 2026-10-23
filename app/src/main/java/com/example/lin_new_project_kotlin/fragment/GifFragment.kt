package com.example.lin_new_project_kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.lin_new_project_kotlin.databinding.FragmentGifBinding
import com.example.lin_new_project_kotlin.viewBinding.BaseBindingFragment


class GifFragment : BaseBindingFragment<FragmentGifBinding?>() {
    override fun onCreateViewBinding(layoutInflater: LayoutInflater): FragmentGifBinding {
        return FragmentGifBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}