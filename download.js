var fs = require('fs'),
    request = require('request'),
    log = require('npmlog'),
    AdmZip = require('adm-zip');

if (fs.existsSync()) {
    var zip = new AdmZip(__dirname + '/libs.zip');
    zip.extractAllTo(__dirname, true);
    console.log("解压完成");
} else {
    var stream = fs.createWriteStream(__dirname + '/libs.zip');
    request("https://hello1024.oss-cn-beijing.aliyuncs.com/libs.zip").pipe(stream)
        .on('close', function () {
            console.log('下载完成');
            var zip = new AdmZip(__dirname + '/libs.zip');
            zip.extractAllTo(__dirname, true);
            console.log("解压完成");
        })
        .on('response', function (response) {
            var length = parseInt(response.headers['content-length'], 10);
            var progress = log.newItem('', length);
            if (process.env.npm_config_progress === 'true') {
                log.enableProgress();
                response.on('data', function (chunk) {
                    progress.completeWork(chunk.length);
                }).on('end', progress.finish);
            }
        });
}

