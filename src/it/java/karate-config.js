function fn() {
    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);
    var port = karate.properties['local.server.port'];

    var config = {baseURL: 'http://localhost:' + port + '/api/calculator'};

    if (!port) {
        karate.log(' ------- Local port not found -------');

    } else {
        karate.log('------- base url is : ' + config.baseURL + '--------');
    }

    return config;
}