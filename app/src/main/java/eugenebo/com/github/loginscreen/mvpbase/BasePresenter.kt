package eugenebo.com.github.loginscreen.mvpbase

import java.lang.ref.WeakReference

abstract class BasePresenter<V : BaseView> {
    private var viewRef: WeakReference<V>? = null

    val view: V?
        get() = viewRef?.get()

    internal fun attachView(view: V) {
        viewRef = WeakReference(view)
        onAttach()
    }

    internal fun detachView() {
        onDetach()
        viewRef?.clear()
    }

    protected fun onAttach() {}

    protected fun onDetach() {}
}
