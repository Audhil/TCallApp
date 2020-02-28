package com.example.truecallerapp.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseLifeCycleActivity<B : ViewDataBinding, T : ViewModel> : BaseActivity() {

    abstract fun getBindingVariable(): Int

    abstract fun initErrorObserver()

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract val viewModelClass: Class<T>

    val viewModel: T by lazy { ViewModelProviders.of(this, factory).get(viewModelClass) }

    lateinit var viewDataBinding: B

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        initErrorObserver()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.apply {
            setVariable(getBindingVariable(), viewModel)
            executePendingBindings()
        }
    }
}