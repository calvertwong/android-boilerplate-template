package com.calvert.aa.core.sharedComponent.alertDialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// We can create an alert dialog without using a dialog fragment but when you rotate the screen
// the alert dialog will be removed unlike a dialog fragment which has a lifecycle
class AbtAlertDialog(private val alertDialogDTO: AlertDialogDTO) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        with(builder) {
            alertDialogDTO.title?.let { title ->
                this.setTitle(title)
            }
            alertDialogDTO.message?.let { message ->
                this.setMessage(message)
            }
            alertDialogDTO.positiveButtonText?.let { positiveText ->
                this.setPositiveButton(positiveText, alertDialogDTO.positiveAction)
            }
            alertDialogDTO.negativeButtonText?.let { negativeText ->
                this.setNegativeButton(negativeText, alertDialogDTO.negativeAction)
            }
            alertDialogDTO.neutralButtonText?.let { neutralText ->
                this.setNeutralButton(neutralText, alertDialogDTO.neutralAction)
            }
        }
        return builder.create()
    }
}