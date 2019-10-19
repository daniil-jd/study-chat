'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('#connecting');

var wsConnection = null;
var username = null;


function connect() {
    console.log('conn is success');
    username = document.querySelector('#username').innerText.trim();

    wsConnection = new WebSocket('/ws/chat');
    console.log('conn is success');
    wsConnection.binaryType = 'blob';
}

console.log('hello js');
connect();


messageForm.addEventListener('submit', sendMessage, true);