package com.calvert.aa.core.logger

import timber.log.Timber

object AbtTimber {
    fun v(msg: String) {
        Timber.v(msg)
    }

    fun d(msg: String) {
        Timber.d(msg)
    }

    fun e(msg: String) {
        Timber.e(msg)
    }
}