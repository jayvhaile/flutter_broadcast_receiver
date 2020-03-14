package com.jvhaile.broadcast_receiver

import android.content.Context

object SharedPreferenceHelper {
    private const val SHARED_PREFERENCES_KEY = "broadcast_receiver_plugin_cache"
    private const val CALLBACK_DISPATCHER_HANDLE_KEY = "callback_dispatch_handler"
    private const val CALLBACK_HANDLE_KEY = "callback_handle"

    private fun Context.prefs() = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

    fun saveCallbackDispatcherHandleKey(ctx: Context, callbackHandle: Long) {
        ctx.prefs()
                .edit()
                .putLong(CALLBACK_DISPATCHER_HANDLE_KEY, callbackHandle)
                .apply()
    }

    fun getCallbackHandle(ctx: Context): Long {
        return ctx.prefs().getLong(CALLBACK_DISPATCHER_HANDLE_KEY, -1L)
    }

    fun hasCallbackHandle(ctx: Context) = ctx.prefs().contains(CALLBACK_DISPATCHER_HANDLE_KEY)
}