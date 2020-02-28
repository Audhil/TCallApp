package com.example.truecallerapp.ui.main

import io.reactivex.disposables.Disposable

interface IMainRepo {
    fun grabDataFromServer(): Disposable
}