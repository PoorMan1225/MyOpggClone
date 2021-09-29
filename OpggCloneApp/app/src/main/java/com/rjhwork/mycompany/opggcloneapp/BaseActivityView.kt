package com.rjhwork.mycompany.opggcloneapp

interface BaseActivityView<T: BaseActivityPresenter> {
    val presenter:T
}