package eugenebo.com.github.loginscreen.loginscreen

import eugenebo.com.github.loginscreen.model.AfterValidationInputState
import eugenebo.com.github.loginscreen.mvpbase.BaseView

interface LoginView : BaseView {
    fun updateUI(afterValidationInputState: AfterValidationInputState)
    fun showSuccessScreen()
    fun showFailureScreen()
}
