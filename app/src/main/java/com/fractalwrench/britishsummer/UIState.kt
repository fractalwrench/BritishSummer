package com.fractalwrench.britishsummer

import com.fractalwrench.britishsummer.weather.ViewPosition

/**
 * Represents 4 possible states of UI - content, error, progress, and placeholder
 */
sealed class UIState<T> {

    /**
     * The UI should display content according to the enclosed data
     */
    class Content<T>(val data: T) : UIState<T>()

    /**
     * The UI should display an error as the content could not be loaded
     */
    class Error<T> : UIState<T>()

    /**
     * The UI should display progress as the content is currently being loaded
     */
    class Progress<T> : UIState<T>()

    /**
     * The UI should display a placeholder as the content cannot be loaded without user interaction
     */
    class Placeholder<T> : UIState<T>()
}

internal fun UIState<*>.toViewPosition(): ViewPosition {
    return when (this) {
        is UIState.Content -> ViewPosition.CONTENT
        is UIState.Error -> ViewPosition.ERROR
        is UIState.Progress -> ViewPosition.PROGRESS
        is UIState.Placeholder -> ViewPosition.PLACEHOLDER
    }
}