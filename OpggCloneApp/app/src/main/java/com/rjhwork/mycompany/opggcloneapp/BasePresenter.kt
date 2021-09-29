package com.rjhwork.mycompany.opggcloneapp

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

interface BasePresenter {

    var scope: CoroutineScope

    fun onCreateView()

    fun onDestroyView()

    @CallSuper
    fun onDestroy() {
        scope.cancel()
    }
}