package com.example.lin_new_project_kotlin.popupwindow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.lin_new_project_kotlin.General
import com.example.lin_new_project_kotlin.R
import com.example.lin_new_project_kotlin.databinding.PopuWindowBackgroundBinding
import com.example.lin_new_project_kotlin.`fun`.CommonUtils.Companion.getColorByVERSION
import com.example.lin_new_project_kotlin.viewBinding.BasePopupWindow


class BackgroundWindow(context: Context?, isFloating: Boolean, isBackground: Boolean) :
    BasePopupWindow<PopuWindowBackgroundBinding?>(
        context,
        0,
        0,
        0,
        General.TRANSLATION_Y,
        false,
        false,
        isFloating,
        false
    ) {
    init {
        if (isBackground) {
            getBinding()!!.bg.setAlpha(0.8f)
            getBinding()!!.bg.setBackgroundColor(getColorByVERSION(R.color.black))
        } else {
            getBinding()!!.bg.setAlpha(1f)
            getBinding()!!.bg.setBackgroundColor(getColorByVERSION(R.color.white))
        }
        //        getBinding().bg.setBackgroundColor(getColorByVERSION(R.color.black));
//        getBinding().bg.setBackground(blurBitmap(10));
//        getBinding().bg.setBackground(blur(context,R.drawable.bg_blue)) ;
    }

    override fun onCreateViewBinding(layoutInflater: LayoutInflater): PopuWindowBackgroundBinding {
        return PopuWindowBackgroundBinding.inflate(layoutInflater)
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)

        //        Looper.myQueue().addIdleHandler(() -> {
//
//            return false;
//        });
    }
}
