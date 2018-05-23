//
//  AMapPlugin.m
//
//  Created by anasit on 2018/5/12.
//  Copyright © 2018年 anasit. All rights reserved.
//

#import "AMapPlugin.h"

@implementation AMapPlugin{
    CDVPluginResult* pluginResult;
}

- (void)pluginInitialize
{
    NSDictionary* infoDict = [[NSBundle mainBundle] infoDictionary];
    [AMapServices sharedServices].apiKey = [infoDict objectForKey:@"AMapAppKey"];
    self.locationManager = [[AMapLocationManager alloc] init];
    // 一次还不错的定位
    [self.locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];
    // 高精度
    // [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
    self.locationManager.delegate = self.appDelegate;
    self.locationManager.locationTimeout =10;
    self.locationManager.reGeocodeTimeout = 10;
}

- (void)getMyLocation:(CDVInvokedUrlCommand*)command
{
    NSLog(@"AmapPlugin");
    
    [self.locationManager requestLocationWithReGeocode:YES completionBlock:^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
        
        if (error)
        {
            NSLog(@"locError:{%ld - %@};", (long)error.code, error.localizedDescription);
            
            if (error.code == AMapLocationErrorLocateFailed)
            {
                return;
            }
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"定位失败"];
        }
        NSLog(@"location:%@", location);
        if (regeocode)
        {
            NSLog(@"reGeocode:%@", regeocode);
            NSDictionary *addressInfo = @{@"Latitude": [NSNumber numberWithDouble:location.coordinate.latitude],
                                          @"Longitude": [NSNumber numberWithDouble:location.coordinate.longitude],
                                          @"Province": regeocode.province ?: @"",
                                          @"City": regeocode.city ?: @"",
                                          @"District": regeocode.district ?: @"",
                                          @"Address": regeocode.formattedAddress ?: @"",
                                          @"Street": regeocode.street ?: @""};
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:addressInfo];
        }else{
            NSDictionary *point = @{@"Latitude": [NSNumber numberWithDouble:location.coordinate.latitude],
                                    @"Longitude": [NSNumber numberWithDouble:location.coordinate.longitude]};
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:point];
        }
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)stopMyLocation:(CDVInvokedUrlCommand *)command{
     pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"IOS无关闭方法"];
     [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
