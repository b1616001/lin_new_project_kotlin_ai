package com.example.lin_new_project_kotlin.fragment

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.lin_new_project_kotlin.OpenGLR.OpenGLRenderer
import com.example.lin_new_project_kotlin.databinding.FragmentGlsurfaceBinding
import com.example.lin_new_project_kotlin.viewBinding.BaseBindingFragment


class GLSurfaceFragment : BaseBindingFragment<FragmentGlsurfaceBinding?>() {
    override fun onCreateViewBinding(layoutInflater: LayoutInflater): FragmentGlsurfaceBinding {
        return FragmentGlsurfaceBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val view = GLSurfaceView(getContext())
        view.setRenderer(OpenGLRenderer())
        getBinding()!!.frameLayout.addView(view)
    }
}