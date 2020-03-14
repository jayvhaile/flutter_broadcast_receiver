#import "BroadcastReceiverPlugin.h"
#if __has_include(<broadcast_receiver/broadcast_receiver-Swift.h>)
#import <broadcast_receiver/broadcast_receiver-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "broadcast_receiver-Swift.h"
#endif

@implementation BroadcastReceiverPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftBroadcastReceiverPlugin registerWithRegistrar:registrar];
}
@end
