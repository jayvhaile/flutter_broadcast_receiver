package com.jvhaile.broadcast_receiver_example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterCallbackInformation
import io.flutter.view.FlutterMain

class TestBroadcastReceiver : BroadcastReceiver(), MethodChannel.MethodCallHandler {
    private lateinit var backgroundChannel: MethodChannel
    private lateinit var engine: FlutterEngine
    private lateinit var intent: Intent
    override fun onReceive(context: Context, intent: Intent) {
      print("")

    }

    override fun onMethodCall(call: MethodCall, r: MethodChannel.Result) {
       
    }

}