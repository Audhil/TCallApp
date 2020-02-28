package com.example.truecallerapp.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.truecallerapp.base.BaseTest
import com.example.truecallerapp.model.ResponseBodyWrapper
import com.example.truecallerapp.ui.main.MainRepo
import io.mockk.*
import io.reactivex.Flowable
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainRepoTest : BaseTest() {

    @get:Rule
    val instantExecutorRule by lazy {
        InstantTaskExecutorRule()
    }

    @get:Rule
    val initSetUpTestRule by lazy {
        InitSetUpTestRule()
    }

    private var mainRepo: MainRepo? = null

    @Before
    fun `set up`() {
        mainRepo = spyk(
            MainRepo(
                api,
                errorLiveData,
                testScheduler
            )
        )
    }

    @After
    fun `tear down`() {
        mainRepo = null
    }

    @Test
    fun `fetchBlog happy case`() {
        val mockResponseBody = mockk<ResponseBody>()
        every { mockResponseBody.string() } returns "{\"response\": \"name\"}"
        every { api.grabBlog() } returns Flowable.just(mockResponseBody)

        val responseBodyWrapper =
            ResponseBodyWrapper(mockResponseBody, mockResponseBody, mockResponseBody)

        mainRepo?.grabDataFromServer()

        val arg1 = slot<ResponseBodyWrapper>()
        val arg2 = slot<String>()
        verify(exactly = 1) {
            mainRepo?.onSuccess(
                capture(arg1), capture(arg2)
            )
        }
        assert(arg1.captured == responseBodyWrapper)
        assert(arg2.captured == "TAG")
        assert(mainRepo?._responseWrapperLiveData?.value != null)
        assert(mainRepo?._responseWrapperLiveData?.value?.t10thChar == 'e')
        assert(mainRepo?._responseWrapperLiveData?.value?.e10thChar == "{e} {\"} ")
        assert(mainRepo?._responseWrapperLiveData?.value?.wordCountMap?.size == 2)
    }
}