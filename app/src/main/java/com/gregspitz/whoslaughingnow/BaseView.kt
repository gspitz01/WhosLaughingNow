package com.gregspitz.whoslaughingnow

/**
 * An interface for all views
 */
interface BaseView<in T : BasePresenter> {
    fun setPresenter(presenter: T)
}
