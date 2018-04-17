var exec = require('cordova/exec');

var AMap = {
    getMyLocation: function (success, error) {
        exec(success, error, 'AMapPlugin', 'getMyLocation', []);
    },
    stopMyLocation: function (success, error) {
        exec(success, error, 'AMapPlugin', 'stopMyLocation', []);
    }
};

module.exports = AMap;
