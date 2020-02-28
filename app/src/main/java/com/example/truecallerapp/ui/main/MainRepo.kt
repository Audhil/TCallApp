package com.example.truecallerapp.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.truecallerapp.model.ResponseBodyWrapper
import com.example.truecallerapp.model.ResponseWrapper
import com.example.truecallerapp.network.API
import com.example.truecallerapp.rx.makeFlowableRxConnection
import com.example.truecallerapp.ui.base.BaseRepo
import com.example.truecallerapp.util.ErrorLiveData
import com.example.truecallerapp.util.NetworkError
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.TestScheduler
import okhttp3.ResponseBody
import java.util.regex.Pattern
import javax.inject.Inject

class MainRepo
@Inject
constructor(
    private val api: API,
    errorLiveDataa: ErrorLiveData,
    private val testScheduler: TestScheduler? = null
) : BaseRepo(errorLiveDataa), IMainRepo {

    val TAG = "TAG"

    val _responseWrapperLiveData by lazy {
        MutableLiveData<ResponseWrapper>()
    }

    override fun grabDataFromServer(): Disposable =
        Flowable.combineLatest(
            api.grabBlog(), //  request 1
            api.grabBlog(), //  request 2
            BiFunction<ResponseBody, ResponseBody, ResponseBodyWrapper> { response1, response2 ->
                ResponseBodyWrapper(response1 = response1, response2 = response2)
            })
            .zipWith(
                api.grabBlog(), //  request 3
                BiFunction<ResponseBodyWrapper, ResponseBody, ResponseBodyWrapper> { responseWrapper, response3 ->
                    responseWrapper.response3 = response3
                    responseWrapper
                })
            .makeFlowableRxConnection(this, TAG, testScheduler)

    override fun onSuccess(obj: Any?, tag: String) {
        when (tag) {
            TAG ->
                (obj as? ResponseBodyWrapper)?.let {
                    val responseWrapper = ResponseWrapper()
                    // 1. 10th char
                    Observable
                        .just(it.response1?.string())
                        .map {
                            it.toList()
                        }
                        .flatMap {
                            Observable.fromIterable(it)
                        }
                        .elementAt(9)
                        .doOnError {
                            errorLiveData.setNetworkError(NetworkError.UNKNOWN)
                        }
                        .doOnSuccess { ch ->
                            responseWrapper.t10thChar = ch
                        }
                        .subscribe()

                    //  2. every 10th char
                    val stringBuilder = StringBuilder()
                    Observable
                        .just(it.response2?.string())
                        .map {
                            it.toList()
                        }
                        .flatMap {
                            Observable.fromIterable(it.withIndex())
                        }
                        .filter {
                            (it.index % 9) == 0
                        }
                        .skip(1)
                        .doOnError {
                            errorLiveData.setNetworkError(NetworkError.UNKNOWN)
                        }
                        .doOnNext { t ->
                            stringBuilder.append("{${t.value}} ")
                        }
                        .subscribe()
                    responseWrapper.e10thChar = stringBuilder.toString()

                    //  3. occurrence of unique words
                    val mutableMap = mutableMapOf<String, Int>()
                    Observable
                        .just(it.response3?.string())
                        .map {
                            it.split(Pattern.compile("\\s+"))
                        }
                        .flatMap {
                            Observable.fromIterable(it)
                        }
                        .doOnError {
                            errorLiveData.setNetworkError(NetworkError.UNKNOWN)
                        }
                        .doOnNext {
                            if (mutableMap.contains(it))
                                mutableMap[it]?.let { oldValue ->
                                    mutableMap[it] = oldValue + 1
                                }
                            else
                                mutableMap[it] = 1
                        }
                        .subscribe()
                    responseWrapper.wordCountMap = mutableMap
                    _responseWrapperLiveData.value = responseWrapper
                }

            else ->
                Unit
        }
    }
}