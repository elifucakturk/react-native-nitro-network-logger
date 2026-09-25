package com.margelo.nitro.HelloWorld

import android.content.Intent
import com.margelo.nitro.NitroModules

class HybridHelloWorld : HybridHelloWorldSpec() {

    private val logs = mutableListOf<String>()

    override fun getMessage(): String {
        return "Hello World"
    }

    init {
        instance = this
    }

    // Her çağrıda 1 REQUEST kaydı eklenir.
    // En fazla son 10 REQUEST tutulur.
    override fun appendLog(log: String): Boolean {
        logs.add(log)

        if (logs.size > 10) {
            logs.removeAt(0)
        }

        return true
    }

  override fun openLogPanels() {
    val activity = NitroModules.applicationContext?.currentActivity ?: return

    val intent = Intent(activity, LogActivity::class.java)

    activity.startActivity(intent)
}

    // Native tarafta tutulan REQUEST'leri React Native'e gönderir.
    override fun getLogs(): Array<String> {
        return logs.toTypedArray()
    }
    fun getLogsForNativePage(): List<String> {
    return logs.toList()
}

    companion object {
        var instance: HybridHelloWorld? = null
    }
}