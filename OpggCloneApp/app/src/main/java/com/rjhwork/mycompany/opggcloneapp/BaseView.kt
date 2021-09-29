package com.rjhwork.mycompany.opggcloneapp

interface BaseView<T: BasePresenter> {
    val presenter: T
}