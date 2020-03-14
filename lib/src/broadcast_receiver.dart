import 'dart:async';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BroadcastReceiver {
  static const MethodChannel _channel =
      const MethodChannel('com.jvhaile.broadcast_receiver/foreground');

  static const MethodChannel _backgroundChannel =
      MethodChannel('com.jvhaile.broadcast_receiver/background');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void executeTask(final void Function(Map<String, dynamic>) onIntent) {
    WidgetsFlutterBinding.ensureInitialized();
    _backgroundChannel.setMethodCallHandler((call) async {
      return onIntent((call.arguments).cast<String, dynamic>());
    });
    _backgroundChannel.invokeMethod("onInitialized");
  }

  static Future<void> initialize(Function callbackDispatcher) async {
    final callback = PluginUtilities.getCallbackHandle(callbackDispatcher);
    assert(
      callback != null,
      "The callbackDispatcher needs to be either a static function or a top level function to be accessible as a Flutter entry point.",
    );
    final int handle = callback.toRawHandle();
    await _channel.invokeMethod(
      'initialize',
      {'callbackHandle': handle},
    );
  }
}
