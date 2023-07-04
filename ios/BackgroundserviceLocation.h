
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNBackgroundserviceLocationSpec.h"

@interface BackgroundserviceLocation : NSObject <NativeBackgroundserviceLocationSpec>
#else
#import <React/RCTBridgeModule.h>

@interface BackgroundserviceLocation : NSObject <RCTBridgeModule>
#endif

@end
