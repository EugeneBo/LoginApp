package eugenebo.com.github.loginscreen.model

import android.os.AsyncTask
import eugenebo.com.github.loginscreen.mvpbase.Callback

class MockAuthorisationService(private val callback: Callback) : AsyncTask<UserCredentials, Unit, Boolean>() {

    private val validEmail = "admin@gmail.com"
    private val validPass = "admin123"

    override fun doInBackground(vararg params: UserCredentials):Boolean {
        Thread.sleep(7_000)
        return validCredentials(params[0].email, params[0].pass)
    }

    private fun validCredentials(email: String, pass: String): Boolean {
        return email == validEmail &&
                pass == validPass
    }

    override fun onPostExecute(result: Boolean) {
       if (result) callback.onSuccess()
        else callback.onFailure()
    }
}