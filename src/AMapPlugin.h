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

@interface AMapPlugin : CDVPlugin
{
}

@property (nonatomic, strong) AMapLocationManager *locationManager;

- (void)getMyLocation:(CDVInvokedUrlCommand*)command;

- (void)stopMyLocation:(CDVInvokedUrlCommand*)command;

@end
