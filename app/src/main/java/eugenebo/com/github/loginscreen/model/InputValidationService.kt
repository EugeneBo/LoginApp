package eugenebo.com.github.loginscreen.model

import java.util.regex.Pattern

class InputValidationService {

    private val validEmailRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    private val validPassRegex =
            Pattern.compile("^[a-zA-Z0-9]*$", Pattern.CASE_INSENSITIVE)

    fun validate(email: String, pass: String): AfterValidationInputState {

        return AfterValidationInputState(
                isValidEmail(email),
                isPassLengthValid(pass),
                hasPassLatinCharAndDigits(pass)
        )
    }

    private fun hasPassLatinCharAndDigits(pass: String): Boolean {
        val matcher = validPassRegex.matcher(pass)
        return matcher.find()
    }

    private fun isPassLengthValid(pass: String): Boolean {
        return pass.length in 6..40
    }


    private fun isValidEmail(email: String): Boolean {
        val matcher = validEmailRegex.matcher(email)
        return matcher.find()
    }


}

