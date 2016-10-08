var stompClient = null;
var boardId = '0';
var sectionId = '0';

createTicket();
//createSection();

function createSection() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.send(`/boards/${boardId}/sections/create`, {}, JSON.stringify({content: 'ticket content', type: 'add', name: 'test' + Date.now(), payload: 'test' + Date.now()}));
        stompClient.subscribe(`/boards/${boardId}`, function (greeting) {
            console.log(greeting.body);
        });
    });
}

function createTicket() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.send(`/boards/${boardId}/sections/${sectionId}/tickets/create`, {}, JSON.stringify({content: 'ticket content', type: 'add', name: 'test' + Date.now(), payload: 'test' + Date.now()}));
        stompClient.subscribe(`/boards/${boardId}`, function (greeting) {
            console.log(greeting.body);
        });
    });
}
