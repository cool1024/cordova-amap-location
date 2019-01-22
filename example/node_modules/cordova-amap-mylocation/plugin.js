var exec = require('cordova/exec');

var CAMap = {
    _getMyLocation: function (success, error) {
        exec(success, error, 'AMapPlugin', '_getMyLocation', []);
    },
    getMyLocation: function (success, error) {
        exec(success, error, 'AMapPlugin', 'getMyLocation', []);
    },
    checkGPS: function (success, error) {
        exec(success, error, 'AMapPlugin', 'checkGPS', []);
    },
    openGPS: function (success, error) {
        exec(success, error, 'AMapPlugin', 'openGPS', []);
    },
    // 这个方法已经不需要了
    stopMyLocation: function (success, error) {
        exec(success, error, 'AMapPlugin', 'stopMyLocation', []);
    }
};

module.exports = CAMap;
