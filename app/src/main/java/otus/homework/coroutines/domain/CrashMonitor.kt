package otus.homework.coroutines.domain

import android.util.Log

object CrashMonitor {

    /**
     * Pretend this is Crashlytics/AppCenter
     */
    fun trackWarning(throwable: Throwable, tag: String) {
        Log.e(tag, throwable.message ?: "Unknown error")
    }
}