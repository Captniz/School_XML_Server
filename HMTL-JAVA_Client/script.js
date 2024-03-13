//GLOBAL VARS
var socket = new WebSocket("ws://localhost:8080");
        

//MAIN
sendRequest("#LIST GROUP", "GROUPS");


//EVENTS
socket.onmessage = function(event) {
    var data = event.data;
    var divId = event.target.rtype;
    
    if (rtype == "example") {
        //Do something
    }
};

socket.onopen = function(event) {
    console.log("Connected to server.");
};

socket.onclose = function(event) {
    console.log("Connection closed.");
    socket.send("#EXIT");
};

window.onbeforeunload = function(e){
    socket.send("#EXIT");
};

//FUNCS
function sendRequest(req, rtype) {
    socket.send(req);
    socket.onmessage.type = rtype;
}

//EXAMPLES
