/*  Task:
    [ ] Нужно сверстать и запрограммировать окно логинки (почтовый ящик + пароль), с обязательными обработками:
        - проверка данных на валидность (почтовый ящик по общим стандартам, пароль содержит латинские буквы и цифры, от 6 до 40 символов)
        - кнопка логина становится активной только в момент валидность всех данных
    [ ] кнопка отправляет запрос (запрос имитировать таймаутом), в результате показывается окно ошибки либо приложение переходит на следующий экран
    [ ] также верстка приложения должна реагировать на показ клавиатуры и всегда вмещать поля пароля и почты в видимую область экрана
    [ ] Пароль можно скрыть/показать по тапу на кнопку справа от текстового поля (обычно с картинкой глаза) */

package eugenebo.com.github.loginscreen.loginscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ImageView
import eugenebo.com.github.loginscreen.model.AfterValidationInputState
import eugenebo.com.github.loginscreen.model.UserCredentials
import eugenebo.com.github.loginscreen.mvpbase.AfterTextChangedListener
import eugenebo.com.github.loginscreen.mvpbase.BaseActivity
import eugenebo.com.github.loginscreen.successscreen.SuccessActivity
import eugenebo.com.github.loginscreen.R
import eugenebo.com.github.loginscreen.failuredialog.FailureAuthDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginView, LoginPresenter>(), LoginView {

    private val progressBarTag =
            "eugenebo.com.github.loginscreen.loginscreen_progressBarTag"
    private var isProgressBarEnabled = false

    private val userEmail: String
        get() = emailEditText.text.toString()

    private val userPass: String
        get() = passEditText.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState != null)
            isProgressBarEnabled = savedInstanceState.getBoolean(progressBarTag)

        val listener = object : AfterTextChangedListener() {
            override fun afterTextChanged(s: Editable) {
                presenter?.onTextChanged(
                        userEmail,
                        userPass)
            }
        }

        emailEditText.addTextChangedListener(listener)
        passEditText.addTextChangedListener(listener)

        loginButton.isEnabled = false

        loginButton.setOnClickListener {
            setProgressBarEnable(true)
            val userCredentials = UserCredentials(userEmail, userPass)
            presenter?.onLoginButtonClicked(userCredentials)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isProgressBarEnabled) {
            setProgressBarEnable(true)
        }
    }

    override fun providePresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun updateUI(inputState: AfterValidationInputState) {

        if (emailEditText.text?.length != 0)
            changeCheckBoxState(emailCheckBox, inputState.isEmailValid)
        else
            emailCheckBox.setImageDrawable(null)


        if (passEditText.text?.length != 0) {
            changeCheckBoxState(passLatinLettersAndDigitsCheckBox, inputState.isPassHasLatinLettersAndNumbers)
            changeCheckBoxState(passLengthCheckBox, inputState.isPassLengthValid)
        } else {
            passLengthCheckBox.setImageDrawable(null)
            passLatinLettersAndDigitsCheckBox.setImageDrawable(null)
        }

        loginButton.isEnabled = inputState.isEmailValid &&
                inputState.isPassHasLatinLettersAndNumbers &&
                inputState.isPassLengthValid
    }

    private fun changeCheckBoxState(checkBox: ImageView, isValid: Boolean) {
        if (isValid)
            checkBox.setImageResource(R.drawable.ic_check_16dp)
        else
            checkBox.setImageResource(R.drawable.ic_not_valid_16dp)
    }

    override fun showSuccessScreen() {
        val intent = Intent(this, SuccessActivity::class.java)
        startActivity(intent)
        setProgressBarEnable(false)
    }

    override fun showFailureScreen() {
        FailureAuthDialog().show(supportFragmentManager, "FailureDialog")
        setProgressBarEnable(false)
    }


    private fun setProgressBarEnable(isEnable: Boolean) {
        if (isEnable) {
            progressBar.visibility = View.VISIBLE
            loginButton.isEnabled = false
            emailEditText.isEnabled = false
            passEditText.isEnabled = false
            loginButton.text = null
        } else {
            progressBar.visibility = View.GONE
            loginButton.isEnabled = true
            emailEditText.isEnabled = true
            passEditText.isEnabled = true
            loginButton.setText(R.string.button_login)
        }
        isProgressBarEnabled = isEnable
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(progressBarTag, isProgressBarEnabled)
        super.onSaveInstanceState(outState)
    }
}
