package com.fractalwrench.britishsummer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fractalwrench.britishsummer.log.Logger
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    protected val logger: Logger by inject()
    protected var compositeDisposable: CompositeDisposable? = null
    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroyView() {
        compositeDisposable?.dispose()
        super.onDestroyView()
    }

}