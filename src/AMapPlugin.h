//
//  RocordPlugin.h
//  FirstObj
//
//  Created by anasit on 2018/5/12.
//  Copyright © 2018年 anasit. All rights reserved.
//

#import <Cordova/CDVPlugin.h>

@interface RecordPlugin : CDVPlugin
{
}

- (void)startRecord:(CDVInvokedUrlCommand *)command;
- (void)stopRecord:(CDVInvokedUrlCommand *)command;
- (void)playRecord:(CDVInvokedUrlCommand *)command;
- (void)stopPlay:(CDVInvokedUrlCommand *)command;

@end
