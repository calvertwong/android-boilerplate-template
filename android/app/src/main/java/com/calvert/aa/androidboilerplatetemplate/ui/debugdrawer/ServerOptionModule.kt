package com.calvert.aa.androidboilerplatetemplate.ui.debugdrawer

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.calvert.aa.core.CoreConstants
import com.calvert.aa.androidboilerplatetemplate.R
import com.calvert.aa.androidboilerplatetemplate.databinding.DdLayoutServerOptionBinding
import io.palaima.debugdrawer.base.DebugModuleAdapter
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class ServerOptionModule : DebugModuleAdapter(), KoinComponent, AdapterView.OnItemSelectedListener {

    private val sharedPreferences: SharedPreferences by inject(named("provideSharedPreferences"))
    private val appContext: Context by inject(named("provideAppContext"))

    private val serverEnvArray = arrayOf(CoreConstants.MOCK, CoreConstants.PROD)

    private lateinit var binding: DdLayoutServerOptionBinding

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        binding = DdLayoutServerOptionBinding.inflate(inflater, parent, false)

        val serverOptionAdapter = ArrayAdapter(appContext, android.R.layout.simple_spinner_item, serverEnvArray)
        serverOptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerServerType.apply {
            adapter = serverOptionAdapter
            setSelection(sharedPreferences.getInt(CoreConstants.SERVER_ENV, CoreConstants.MOCK_POSITION))
            onItemSelectedListener = this@ServerOptionModule
        }

        return binding.root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.getItemAtPosition(position).toString() == CoreConstants.MOCK) {
            binding.etServerIp.isVisible = true
            binding.btnSetIp.isVisible = true
            binding.tvConnectedIp.isVisible = true

            setupAdditionalViewsForMock()
        } else {
            binding.etServerIp.isGone = true
            binding.btnSetIp.isGone = true
            binding.tvConnectedIp.isGone = true

            // Insert actual server url here in the future, currently assigning a dummy value
            sharedPreferences.edit().putString(CoreConstants.PROD_SERVER_IP, "https://192.168.1.156:5050").apply()
        }

        sharedPreferences.edit().putInt(CoreConstants.SERVER_ENV, position).apply()
    }

    private fun setupAdditionalViewsForMock() {
        if (sharedPreferences.getString(CoreConstants.MOCK_SERVER_IP, null) != null) {
            val serverIp = sharedPreferences.getString(CoreConstants.MOCK_SERVER_IP, "")
            binding.etServerIp.text = Editable.Factory.getInstance().newEditable(serverIp?.replace("https://", "")?.replace("/", ""))
            binding.tvConnectedIp.text = appContext.getString(R.string.connected_ip, serverIp)
        } else {
            binding.tvConnectedIp.text = appContext.getString(R.string.connected_ip, "")
        }

        binding.btnSetIp.setOnClickListener {
            sharedPreferences.edit().putString(CoreConstants.MOCK_SERVER_IP, "https://${binding.etServerIp.text}/").apply()
            binding.tvConnectedIp.text = appContext.getString(R.string.connected_ip, sharedPreferences.getString(CoreConstants.MOCK_SERVER_IP, ""))
        }
    }
}
