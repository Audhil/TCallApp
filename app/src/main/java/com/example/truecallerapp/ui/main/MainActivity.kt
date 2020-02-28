package com.example.truecallerapp.ui.main

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.Observer
import com.example.truecallerapp.BR
import com.example.truecallerapp.R
import com.example.truecallerapp.databinding.MainActivityBinding
import com.example.truecallerapp.ui.base.BaseLifeCycleActivity
import com.example.truecallerapp.util.NetworkError
import com.example.truecallerapp.util.showToast

class MainActivity : BaseLifeCycleActivity<MainActivityBinding, MainViewModel>() {

    override fun getBindingVariable(): Int = BR.view_model

    override fun getLayoutId(): Int = R.layout.main_activity

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataObserver()

        viewDataBinding.apply {
            t10charTxtView.movementMethod = ScrollingMovementMethod()
            e10charTxtView.movementMethod = ScrollingMovementMethod()
            mapTxtView.movementMethod = ScrollingMovementMethod()

            btn.setOnClickListener {
                viewModel?.getBlog()
            }
        }
    }

    private fun initDataObserver() =
        viewModel.responseWrapperLiveData.observe(this, Observer {
            viewModel.t10CharField.set(
                "10th Char: \n" +
                        if (it.t10thChar == ' ')
                            "(It's just an empty space)"
                        else
                            it.e10thChar
            )
            viewModel.e10CharField.set(
                "every 10th Char: \n" + it.e10thChar
            )
            viewModel.wordCountMapField.set(
                "word counter request: \n" +
                        it.wordCountMap?.let {
                            var valueIs = ""
                            for ((key, value) in it)
                                valueIs += "{$key: $value}\n"

                            valueIs
                        }
            )
            viewModel.progressVisibility.set(false)
        })

    override fun initErrorObserver() =
        viewModel.errorLiveData.observe(this, Observer {
            when (it) {
                NetworkError.UNKNOWN,
                NetworkError.SOCKET_TIMEOUT,
                NetworkError.DISCONNECTED,
                NetworkError.BAD_URL -> {
                    getString(R.string.something_went_wrong).showToast()
                    viewModel.t10CharField.set(getString(R.string.something_went_wrong))
                    viewModel.e10CharField.set(getString(R.string.something_went_wrong))
                    viewModel.wordCountMapField.set(getString(R.string.something_went_wrong))
                }

                else ->
                    Unit
            }
        })
}
