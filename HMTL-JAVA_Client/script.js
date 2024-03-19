//GLOBAL VARS
var socket = new WebSocket("ws://localhost:5567");
        

//MAIN
sendRequest("#LIST_GROUP");


//EVENTS
socket.onmessage = function(event) {
    var data = event.data;
    var divId = event.target.rtype;
    var doc = parseXML(data);
    
    
    if (divId == "#LIST_GROUP") {
        document.getElementById("list").innerHTML = "";

        doc.getElementsByTagName("group").forEach(function(group) {
            var name = group.getAttribute("genre");
            var followers = group.getElementsByTagName("followers")[0].textContent;
            var imagePath = group.getElementsByTagName("imagePath")[0].textContent;
            
            //TODO ON HOVER SHOW INFO, ON CLICK SHOW STREAMS
            var content = "<button class='button' onclick='sendRequest(\"#LIST_STREAMS "+name+"\")'> "+ name + " </button>";
        });
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
function sendRequest(req) {
    socket.send(req);
    socket.onmessage.type = req.split(" ")[0];
}

function parseXML(xmlString) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xmlString, "text/xml");

    return xmlDoc;
}
//EXAMPLES
