# 高德地图Cordova插件

## 相关说明
1. 申请高德地图应用与key [高德开放平台](https://lbs.amap.com)
2. android apk签名/生成签名 [参考文档](https://lbs.amap.com/faq/top/hot-questions/249)
    * 使用android studio -> Build -> Generate Signed Bundle/Apk... 可以生成一个签名文件
    * 查看安全码sha1： keytool -v -list -keystore [keystore文件路径]

## 安装命令
`cordova plugin add cordova-amap-mylocation --variable ANDROID_KEY=ANDROID开发密钥 --variable IOS_KEY=IOS开发密钥`

### 安装例子命令
`cordova plugin add cordova-amap-mylocation --variable ANDROID_KEY=c40acfe3d1a57f5ef0307e031c42d582 --variable IOS_KEY=	6c92e294244e5ea9a8b9ad134e401417`

### 参数说明
1. ANDROID_KEY:   ANDROID开发密钥
2. IOS_KEY:   IOS开发密钥

### 使用方法
1. 直接获取定位
```typescript
// typescript
window.CAMap.getMyLocation(
    success=>{
        alert(JSON.stringify(success));
    },
    error=>{
        alert(error);
    }
);
// javascript
// window.CAMap.getMyLocation(
//     function(success){
//         alert(JSON.stringify(success));
//     },
//     function(error){
//         alert(error);
//     },
// );
```

2. 定时获取
```javascript
// 可以使用setInterval方法定时获取
setInterval(() => {
    window.CAMap && window.CAMap.getMyLocation(
        success => {
            alert(JSON.stringify(success));
        },
        error => {
            alert(error);
        }
    );
}, 1000);
```

3. ANDROID提示打开GPS-只能用于ANDROID,请判断平台是IOS还是ANDROID
```javascript
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
```

### 例子
1. 本项目的example目录中有一个使用`cordova create example`创建的项目(index.js中的代码只能在android平台中使用)

### Android问题
1. 第一次使用的时候由于用户没有授权,定位会失败（建议一启动应用就执行`window.CAMap.checkGPS()`方法，进行授权，而调用定位的方法在对应页面执行即可）
2. 部分设备不打开GPS是无法定位的，定位信息为空（定位失败立刻在进行一次定位，直到得到一个正确的结果，`getMyLocation`方法会打开GPS设置页面）

### 代码更新
1. IOS现在并没有更新到与ANDROID版本一致 |...>_<...|
2. example没有测试IOS的代码，无法在IOS中使用