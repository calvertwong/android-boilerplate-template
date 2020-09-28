package com.calvert.aa.core.coreUtil.errorHandler

import android.content.DialogInterface
import com.calvert.aa.core.sharedComponent.alertDialog.AlertDialogDTO
import com.calvert.aa.core.sharedComponent.alertDialog.AbtAlertDialog
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class AbtErrorHandler : KoinComponent {

    private val moshi: Moshi by inject(named("provideMoshi"))

    fun displayErrorDialog(
        title: String?,
        message: String?,
        positiveButtonText: String?,
        positiveAction: DialogInterface.OnClickListener?
    ): AbtAlertDialog {
        val alertDialog: AbtAlertDialog by inject(named("provideAlertDialog")) {
            parametersOf(
                AlertDialogDTO(
                    title = title,
                    message = message,
                    positiveButtonText = positiveButtonText,
                    positiveAction = positiveAction
                )
            )
        }
        return alertDialog
    }

    fun displayNetworkErrorDialog(): AbtAlertDialog {
        val alertDialog: AbtAlertDialog by inject(named("provideAlertDialog")) {
            parametersOf(
                AlertDialogDTO(
                    title = "Network Error",
                    message = "Sorry, unable to fetch your data.",
                    positiveButtonText = "Close",
                    positiveAction = { dialog, _ ->
                        dialog.dismiss()
                    }
                )
            )
        }
        return alertDialog
    }

    fun displayErrorDialogFromResponseBody(responseBody: ResponseBody?): AbtAlertDialog {
        val parsedResponse = responseBody?.let { parseErrorResponseBody(it) }
        val alertDialog: AbtAlertDialog by inject(named("provideAlertDialog")) {
            parametersOf(
                AlertDialogDTO(
                    title = parsedResponse?.errorTitle,
                    message = parsedResponse?.errorMessage,
                    positiveButtonText = "Close",
                    positiveAction = { dialog, _ ->
                        dialog.dismiss()
                    }
                )
            )
        }
        return alertDialog
    }

    private fun parseErrorResponseBody(responseBody: ResponseBody): ErrorResponseBodyDTO? {
        return moshi.adapter(ErrorResponseBodyDTO::class.java).fromJson(responseBody.string())?.run {
            ErrorResponseBodyDTO(
                errorCode = errorCode,
                errorTitle = errorTitle,
                errorMessage = errorMessage,
                errorHttpCode = errorHttpCode
            )
        }
    }
}

