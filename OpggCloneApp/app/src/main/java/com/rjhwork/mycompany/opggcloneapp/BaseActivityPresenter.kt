package com.rjhwork.mycompany.opggcloneapp

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

interface BaseActivityPresenter {
    var scope: CoroutineScope

    fun onCreate()

    @CallSuper
    fun onDestroy() {
        scope.cancel()
    }
}