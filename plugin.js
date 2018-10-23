var exec = require('cordova/exec');

var CAMap = {
    getMyLocation: function (success, error) {
        exec(success, error, 'AMapPlugin', 'getMyLocation', []);
    },
    stopMyLocation: function (success, error) {
        exec(success, error, 'AMapPlugin', 'stopMyLocation', []);
    }
};

module.exports = CAMap;
