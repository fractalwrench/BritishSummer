package com.fractalwrench.britishsummer

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