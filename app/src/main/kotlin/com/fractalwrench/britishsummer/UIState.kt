package com.fractalwrench.britishsummer

sealed class UIState<T> {
    class Content<T>(val data: T) : UIState<T>()
    class Error<T> : UIState<T>()
    class Progress<T> : UIState<T>()
    class Placeholder<T> : UIState<T>()
}