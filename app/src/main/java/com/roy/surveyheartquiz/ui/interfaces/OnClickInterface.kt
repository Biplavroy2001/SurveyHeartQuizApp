package com.roy.surveyheartquiz.ui.interfaces
/** By ROY */
/**
 * Generic interface for implementing OnClick() functionality to the items of the Adapter
 */
interface OnClickInterface<T> {
    fun onClick(value: T)
}