'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var logoutButton = document.querySelector('#logout');

var wsConnection = null;
var username = null;
var baseURL = 'http://localhost:8080';

const styles = {
    container: {
        height: '75vh',
        overflowY: 'scroll',
        // flex: 1,
    },
    ul: {
        listStyle: 'none',
    },
    li: {
        marginTop: 13,
        marginBottom: 13,
    },
    senderUsername: {
        fontWeight: 'bold',
    },
    senderTime: {
        fontWeight: 'lighter',
        color: '#8f94a1',
    },
    message: { fontSize: 15 },
};

function connect() {
    console.log('conn is success');
    username = document.querySelector('#username').innerText.trim();

    if (username) {
        wsConnection = new WebSocket('ws://localhost:8080/ws/chat');
        wsConnection.binaryType = 'blob';
        console.log('connectio!');

        var message = 'Новый пользователь чата \'' + username + '\'!';

        waitForConnect(message, 'NEW_USER');
        wsConnection.addEventListener('message', onNewMessage);
        wsConnection.addEventListener('message', onNewMessage);
    }
}

function onNewMessage (evt) {
    event.preventDefault();
    if (evt.data instanceof Blob) {
        // значит текстовое сообщение
        return;
    }
    var messages = JSON.parse(evt.data);
    console.log('new messages');
    console.log(messages);

    deleteChild(messageArea);

    messages.forEach(function (message) {
        var messageElement = document.createElement('li');
        messageElement.setAttribute('style', 'fontWeight: \'bold\'');
        console.log('style');
        console.log(messageElement);
        var senderDiv = document.createElement('div');
        var senderSpan = document.createElement('span');
        senderSpan.setAttribute('style', 'fontWeight: \'bold\'');
        // senderSpan.style = styles.senderUsername;
        var senderText = document.createTextNode(message.sender);

        senderSpan.appendChild(senderText);
        senderDiv.appendChild(senderSpan);

        var timeDiv = document.createElement('div');
        var timeSpan = document.createElement('span');
        timeSpan.style = styles.senderTime;
        var timeText = document.createTextNode(moment(new Date(message.created)).calendar());

        timeSpan.appendChild(timeText);
        timeDiv.appendChild(timeSpan);

        var msgDiv = document.createElement('div');
        var msgSpan = document.createElement('span');
        msgSpan.style = styles.message;
        var msgText = document.createTextNode(message.message);

        msgSpan.appendChild(msgText);
        msgDiv.appendChild(msgSpan);

        messageElement.appendChild(senderDiv);
        messageElement.appendChild(timeDiv);
        messageElement.appendChild(msgDiv);
        messageArea.appendChild(messageElement);
    });
}

function onSendMessage(event) {
    event.preventDefault();
    var messageContent = messageInput.value.trim();
    console.log(wsConnection);
    if(messageContent && wsConnection) {
        sendMessage(messageContent, 'MESSAGE');
        messageInput.value = '';
    }
}

function onLogoutButton(event) {
    event.preventDefault();
    var message = 'Пользователь \'' + username + '\' покинул чат.';
    sendMessage(message, 'LOGOUT');
    wsConnection.close();
    wsConnection = null;

    window.location.href = baseURL + '/logout';
}

function sendMessage(message, status) {
    var chatMessage = {
        id: 0,
        sender: username,
        room: 'default',
        message: message,
        created: new Date().getTime(),
        status: status
    };
    console.log(chatMessage);
    wsConnection.send(JSON.stringify(chatMessage));
}

function deleteChild(element) {
    var child = element.lastElementChild;
    while (child) {
        element.removeChild(child);
        child = element.lastElementChild;
    }
}


var waitForConnect = function(message, status) {
    if (wsConnection.readyState == WebSocket.OPEN) {
        sendMessage(message, status);
    } else {
        setTimeout(function() {
            waitForConnect(message, status);
        }, 100);
    }
};

connect();

messageForm.addEventListener('submit', onSendMessage, true);

logoutButton.addEventListener('click', onLogoutButton, true);