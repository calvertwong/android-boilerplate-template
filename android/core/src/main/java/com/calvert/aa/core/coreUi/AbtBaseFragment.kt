package com.calvert.aa.core.coreUi

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.calvert.aa.core.coreUtil.errorHandler.AbtErrorHandler
import com.calvert.aa.core.logger.AbtTimber
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

abstract class AbtBaseFragment : Fragment() {
    val timber: AbtTimber = get(named("provideTimber"))

    val errorHandler: AbtErrorHandler by inject(named("provideAbtErrorHandler"))

    fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }
}