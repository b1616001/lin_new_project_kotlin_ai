package com.example.lin_new_project_kotlin.ai.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.lin_new_project_kotlin.ai.MainActivity
import com.example.lin_new_project_kotlin.ai.enums.LanguageEnum
import com.example.lin_new_project_kotlin.ai.`fun`.LanguageUtils
import com.example.lin_new_project_kotlin.ai.`fun`.MmkvUtils
import com.example.lin_new_project_kotlin.ai.viewBinding.BaseBindingFragment
import com.example.lin_new_project_kotlin_ai.databinding.FragmentLanguageBinding


class LanguageFragment : BaseBindingFragment<FragmentLanguageBinding?>() {
    private var languageEnum: LanguageEnum? = null
    override fun onCreateViewBinding(layoutInflater: LayoutInflater): FragmentLanguageBinding {
        return FragmentLanguageBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageEnum = LanguageEnum.getLanguageEnum(MmkvUtils.getLanguageCountry(), false)
        initView()
    }

    private fun initView() {
        getBinding()!!.btnCn.setOnClickListener { view: View? ->
            setLanguage(LanguageEnum.Chinese)
        }
        getBinding()!!.btnTw.setOnClickListener { view: View? ->
            setLanguage(LanguageEnum.TW)
        }
    }

    fun setLanguage(language: LanguageEnum) {
        languageEnum = language
        LanguageUtils.refreshApp(MainActivity.Companion.mainActivity!!, languageEnum)
    }
}