var websocket = null;

function init() {
	if ("WebSocket" in window) {
		websocket = new WebSocket('ws://localhost:8080/ws/echo');
		websocket.onopen = function(data) {
			document.getElementById("main").style.display = "block";
		};

		websocket.onmessage = function(data) {
			setMessage(JSON.parse(data.data));
		};

		websocket.onerror = function(e) {
			alert('An error occurred, closing application');
			cleanUp();
		};

		websocket.onclose = function(data) { cleanUp(); };
	} else {
		alert("Websocket is not supported by this browser");
	}
}

function cleanUp() {
	document.getElementById("main").style.display = "none";

	websocket = null;
}

function sendMessage() {
	var messageContent = document.getElementById("message").value;
	var message = buildMessage(messageContent);

	document.getElementById("message").value = '';

	setMessage(message);
	websocket.send(JSON.stringify(message));
}

function buildMessage( message) {
	return {
		message : message
	};
}

function setMessage(msg) {
	var currentHTML = document.getElementById('scrolling-messages').innerHTML;
	var newElem;

	newElem = '<p><span>' + msg.message + '</span></p>';

	document.getElementById('scrolling-messages').innerHTML = currentHTML + newElem;
}