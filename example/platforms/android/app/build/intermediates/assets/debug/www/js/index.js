// 设备就绪后，开始定位
document.addEventListener('deviceready', getLocation, false);
var CAMap = window.CAMap;

function errorFunc(error) {
    alert(error);
}

function getLocation() {
    // 检查GPS状态
    CAMap.checkGPS(function (isOpen) {

        alert("当前GPS状态:" + isOpen === 1 ? '开启' : '关闭');

        // 如果没有打开，那么提示用户要打开
        if (isOpen !== 1) {
            // 你可以显示一个提示界面,然后执行定位
            confirm('我们需要使用您的GPS，请到设置中开启')
                && CAMap.getMyLocation(function (location) {
                    alert(JSON.stringify(location));
                }, errorFunc);

        } else {
            // 直接获取定位信息
            CAMap.getMyLocation(function (location) {
                alert(JSON.stringify(location));
            }, errorFunc);
        }
    }, errorFunc);
}