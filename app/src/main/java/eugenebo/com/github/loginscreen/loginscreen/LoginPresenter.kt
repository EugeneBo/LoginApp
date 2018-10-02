package eugenebo.com.github.loginscreen.loginscreen

import eugenebo.com.github.loginscreen.model.UserCredentials
import eugenebo.com.github.loginscreen.mvpbase.BasePresenter
import eugenebo.com.github.loginscreen.model.InputValidationService
import eugenebo.com.github.loginscreen.model.MockAuthorisationService
import eugenebo.com.github.loginscreen.mvpbase.Callback
class LoginPresenter : BasePresenter<LoginView>() {

    private val inputValidationService: InputValidationService = InputValidationService()
    private var mockAuthorisationService: MockAuthorisationService? = null

    fun onTextChanged(email: String, pass: String) {
        val state = inputValidationService.validate(email, pass)
        if (view != null) view?.updateUI(state)
    }

    fun onLoginButtonClicked(userCredentials: UserCredentials) {
        mockAuthorisationService = MockAuthorisationService(object : Callback {

            override fun onSuccess() {
                view?.showSuccessScreen()
            }

            override fun onFailure() {
                view?.showFailureScreen()
            }
        })

        mockAuthorisationService?.execute(userCredentials)
    }

}
