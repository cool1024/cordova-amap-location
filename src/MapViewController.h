//
//  MapViewController.h
//  HelloCordova
//
//  Created by  anasit on 2019/2/9.
//

#import <UIKit/UIKit.h>
#import <MAMapKit/MAMapKit.h>
#import <AMapSearchKit/AMapSearchKit.h>
#import <AMapNaviKit/AMapNaviKit.h>
#import <AMapFoundationKit/AMapFoundationKit.h>
#import "CustomAnnotationView.h"

@interface MapViewController : UIViewController

@property NSArray *marks;

-(void)showNavigate:(MAPointAnnotation *)view;

@end
