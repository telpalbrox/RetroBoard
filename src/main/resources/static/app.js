var stompClient = null;

connect();

function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.send("/boards/77a90787-f4ae-473d-80cc-4b06841fdefc/sections/create", {}, JSON.stringify({type: 'add', name: 'test' + Date.now(), payload: 'test' + Date.now()}));
        stompClient.subscribe('/boards/77a90787-f4ae-473d-80cc-4b06841fdefc', function (greeting) {
            console.log(greeting.body);
        });
    });
}
