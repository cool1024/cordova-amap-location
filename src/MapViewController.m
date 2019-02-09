//
//  MapViewController.m
//  HelloCordova
//
//  Created by  anasit on 2019/2/9.
//

#import "MapViewController.h"

@interface MapViewController ()<AMapSearchDelegate,MAMapViewDelegate>

@end

@implementation MapViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [AMapServices sharedServices].enableHTTPS = YES;
    MAMapView *_mapView = [[MAMapView alloc] initWithFrame:self.view.bounds];
    [self.view addSubview:_mapView];
    _mapView.showsUserLocation = YES;
    _mapView.userTrackingMode = MAUserTrackingModeFollow;
    _mapView.delegate = self;
    [self doSearch:@"电影院"];
}

- (void)doSearch:(NSString*)searchKey{
    AMapSearchAPI *search = [[AMapSearchAPI alloc] init];
    search.delegate = self;
    AMapPOIAroundSearchRequest *request = [[AMapPOIAroundSearchRequest alloc] init];
    request.location = [AMapGeoPoint locationWithLatitude:25.659571 longitude:114.759554];
    request.keywords = searchKey;
    request.sortrule = 0;
    request.requireExtension = YES;
    [search AMapPOIAroundSearch:request];
    NSLog(@"开始查询");
}

- (void)onPOISearchDone:(AMapPOISearchBaseRequest *)request response:(AMapPOISearchResponse *)response
{
    NSLog(@"查询结束");
    for(AMapPOI* poi in response.pois){
        NSLog(@"地点名称：%@",poi.name);
    }
}

- (void)mapView:(MAMapView *)mapView didUpdateUserLocation:(MAUserLocation *)userLocation updatingLocation:(BOOL)updatingLocation{
    if(updatingLocation){
        CLLocation *location = userLocation.location;
        NSLog(@"用户位置变化(%f,%f,%d)",location.coordinate.latitude,location.coordinate.longitude,updatingLocation);
    }
}
@end
