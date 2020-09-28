package com.calvert.aa.core.di.module

import com.calvert.aa.core.coreUtil.errorHandler.AbtErrorHandler
import com.calvert.aa.core.logger.AbtTimber
import com.calvert.aa.core.sharedComponent.alertDialog.AlertDialogDTO
import com.calvert.aa.core.sharedComponent.alertDialog.AbtAlertDialog
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModule = module {
    single(named("provideAppContext")) { androidContext() }

    single(named("provideMoshi")) { Moshi.Builder().build() }

    // 0 = private mode
    single(named("provideSharedPreferences")) {
        androidContext().getSharedPreferences("core_sp", 0)
    }

    factory(named("provideTimber")) { AbtTimber }

    factory(named("provideAlertDialog")) { (param: AlertDialogDTO) -> AbtAlertDialog(param) }

    factory(named("provideAbtErrorHandler")) { AbtErrorHandler() }
}