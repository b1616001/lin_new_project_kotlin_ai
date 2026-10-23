package com.example.lin_new_project_kotlin.ai.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.lin_new_project_kotlin_ai.R
import com.example.lin_new_project_kotlin.ai.`fun`.CodeUtils
import com.example.lin_new_project_kotlin.ai.`fun`.CommonUtils
import com.example.lin_new_project_kotlin.ai.viewBinding.BaseBindingFragment
import com.example.lin_new_project_kotlin_ai.databinding.FragmentQrcodeBinding


class QrcodeFragment : BaseBindingFragment<FragmentQrcodeBinding?>() {
    override fun onCreateViewBinding(layoutInflater: LayoutInflater): FragmentQrcodeBinding {
        return FragmentQrcodeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding()!!.tvQrcode.setOnClickListener { view1: View? ->
            if (CommonUtils.Companion.checkStr(getBinding()!!.edQrcode.getText().toString())) {
                val logo = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red)
                val bitmap = CodeUtils.Companion.createQRCode(getBinding()!!.edQrcode.getText().toString(), 600, logo)
                getBinding()!!.image.setImageBitmap(bitmap)
            }
        }
    }
}