package eugenebo.com.github.loginscreen.mvpbase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>?> : AppCompatActivity() {
    protected var presenter: P? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LoginActivity", "onCreate()" )
        attachPresenter()
    }

    override fun onStart() {
        super.onStart()
        Log.d("LoginActivity", "onStart()" )
        presenter?.attachView(this as V)
    }

    private fun attachPresenter() {
        var presenter: P? = lastCustomNonConfigurationInstance as P
        if (presenter == null) {
            presenter = providePresenter()
            Log.d("BaseActivity", "providePresenter()")
        }
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
        Log.d("LoginActivity", "onDestroy()" )
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    protected abstract fun providePresenter(): P



}
