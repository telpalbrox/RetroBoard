var stompClient = null;
var boardId = 'fe40a505-a3b6-4ff1-b370-d6dda6409e87';
var sectionId = 'b7d034c9-44eb-4e47-8050-aca2d650af31';
var ticketId = '5638b1d6-f143-45dc-b4c3-5d09de34ff19';

//createTicket();
createSection();
//removeTicket();

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

function removeTicket() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.send(`/boards/${boardId}/sections/${sectionId}/tickets/${ticketId}/delete`, {}, JSON.stringify({content: 'ticket content', type: 'add', name: 'test' + Date.now(), payload: 'test' + Date.now()}));
        stompClient.subscribe(`/boards/${boardId}`, function (greeting) {
            console.log(greeting.body);
        });
    });
}
