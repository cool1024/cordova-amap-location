# 高德地图Cordova插件

## 相关说明
1. 申请高德地图应用与key [高德开放平台](https://lbs.amap.com)
2. android apk签名/生成签名 [参考文档](https://lbs.amap.com/faq/top/hot-questions/249)
    * 使用android studio -> Build -> Generate Signed Bundle/Apk... 可以生成一个签名文件
    * 查看安全码sha1： keytool -v -list -keystore [keystore文件路径]

## 安装命令
`cordova plugin add cordova-amap-mylocation --variable API_KEY=安卓平台的key --variable IOS_KEY=苹果的key`

### 安装例子命令
`cordova plugin add cordova-amap-mylocation --variable API_KEY=d9bb7e3959255b4b2d6b844fd8ad31c5 --variable IOS_KEY=f2f5baa023acdfc4d3aca9f88db2bdde`

### 参数说明
1. API_KEY:   安卓平台KEY
2. IOS_KEY:   苹果KEY

### 使用方法
```typescript
window.CAMap.getMyLocation(
    success=>{
        alert(JSON.stringify(success));
    },
    error=>{
        alert(error);
    }
);  
```