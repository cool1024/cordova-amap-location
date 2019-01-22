cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
  {
    "id": "cordova-amap-mylocation.CAMap",
    "file": "plugins/cordova-amap-mylocation/plugin.js",
    "pluginId": "cordova-amap-mylocation",
    "clobbers": [
      "CAMap"
    ]
  }
];
module.exports.metadata = 
// TOP OF METADATA
{
  "cordova-plugin-whitelist": "1.3.3",
  "cordova-amap-mylocation": "0.0.13"
};
// BOTTOM OF METADATA
});