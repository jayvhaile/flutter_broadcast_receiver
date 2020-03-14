import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';

import 'file:///C:/Users/pc/Documents/Project_Files/flutter/broadcast_receiver/lib/src/broadcast_receiver.dart';

void main() {
  const MethodChannel channel = MethodChannel('broadcast_receiver');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await BroadcastReceiver.platformVersion, '42');
  });
}
