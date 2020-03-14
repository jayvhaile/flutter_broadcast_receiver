package com.jvhaile.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Telephony
import android.telephony.SmsMessage
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterCallbackInformation
import io.flutter.view.FlutterMain

class MyBroadcastReceiver : BroadcastReceiver(), MethodChannel.MethodCallHandler {
    private lateinit var backgroundChannel: MethodChannel
    private lateinit var engine: FlutterEngine
    private lateinit var intentMap: Map<String, Any?>
    override fun onReceive(context: Context, intent: Intent) {
        transformIntent(intent)
        FlutterMain.ensureInitializationComplete(context, null)
        engine = FlutterEngine(context)
        FlutterMain.ensureInitializationComplete(context, null)
        val callbackHandle = SharedPreferenceHelper.getCallbackHandle(context)
        val callbackInfo = FlutterCallbackInformation.lookupCallbackInformation(callbackHandle)
        val dartBundlePath = FlutterMain.findAppBundlePath()
        engine.dartExecutor.executeDartCallback(
                DartExecutor.DartCallback(
                        context.assets,
                        dartBundlePath,
                        callbackInfo
                )
        )
        backgroundChannel = MethodChannel(engine.dartExecutor, "com.jvhaile.broadcast_receiver/background")
        backgroundChannel.setMethodCallHandler(this)

    }

    private fun transformIntent(intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val sms = intent.extras?.let { bundle ->
                (bundle.get("pdus") as Array<*>).map {
                    val format = bundle.getString("format")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        SmsMessage.createFromPdu(it as ByteArray, format)
                    else SmsMessage.createFromPdu(it as ByteArray)
                }
            }?.mergedMap()
            intentMap = intent.toMap().toMutableMap().apply {
                this["sms"] = sms
            }
        } else {
            intentMap = intent.toMap()
        }
    }

    override fun onMethodCall(call: MethodCall, r: MethodChannel.Result) {
        when (call.method) {
            "onInitialized" ->
                backgroundChannel.invokeMethod(
                        "onHandleIntent",
                        intentMap,
                        object : MethodChannel.Result {
                            override fun notImplemented() {
                                stopEngine()
                            }

                            override fun error(p0: String?, p1: String?, p2: Any?) {
                                stopEngine()
                            }

                            override fun success(receivedResult: Any?) {
                                stopEngine()
                            }
                        })
        }
    }

    private fun stopEngine() {
        Handler(Looper.getMainLooper()).post {
            engine.destroy()
        }
    }
}