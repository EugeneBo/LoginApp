package eugenebo.com.github.loginscreen.failuredialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import eugenebo.com.github.loginscreen.R

class FailureAuthDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage(R.string.dialog_failure_message)
                .setPositiveButton(R.string.dialog_button) { dialog, id ->
                    dismiss()
                }
        return builder.create()
    }
}