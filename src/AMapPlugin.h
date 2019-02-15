//
//  AMapPlugin.h
//  FirstObj
//
//  Created by anasit on 2018/5/12.
//  Copyright © 2018年 anasit. All rights reserved.
//

#import <Cordova/CDVPlugin.h>
#import <AMapFoundationKit/AMapFoundationKit.h>
#import <AMapLocationKit/AMapLocationKit.h>
#import "MapViewController.h";

@interface AMapPlugin : CDVPlugin
{
}

@property (nonatomic, strong) AMapLocationManager *locationManager;

- (void)getMyLocation:(CDVInvokedUrlCommand*)command;

- (void)stopMyLocation:(CDVInvokedUrlCommand*)command;

- (void)showMap:(CDVInvokedUrlCommand *)command;

// 检查GPS是否开启,IOS无需此方法，默认为开启
- (void)checkGPS:(CDVInvokedUrlCommand *)command;

@end
