package com.fractalwrench.britishsummer

sealed class UiModel<T> {
    class Data<T>(val data: T) : UiModel<T>()
    class Error<T> : UiModel<T>()
    class Progress<T> : UiModel<T>()
    class Placeholder<T> : UiModel<T>()
}