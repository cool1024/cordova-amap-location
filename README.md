## Install
`cordova plugin add cordova-amap-mylocation --variable API_KEY=you_android_key --variable IOS_KEY=you_ios_key`

### Example install
`cordova plugin add cordova-amap-mylocation --variable API_KEY=d9bb7e3959255b4b2d6b844fd8ad31c5 --variable IOS_KEY=f2f5baa023acdfc4d3aca9f88db2bdde`

### Params
1. API_KEY:   Android Key
2. IOS_KEY:   Ios Key

### Usage
```typescript
window.AMap.getMyLocation(
    success=>{
        alert(JSON.stringfy(success));
        window.AMap.stopMyLocation(()=>alert('stop success'),()=>alert('stop error'));
    },
    error=>{
        alert(error);
    }
);  
```