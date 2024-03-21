//GLOBAL VARS
var ws = new WebSocket("ws://localhost:5567");
var reqType = "";

//MAIN

//EVENTS
ws.onmessage = function(event) {
    var data = event.data;
    var doc = parseXML(data);

    console.log("Received data: " + data + " with type: " + event.target.type);
    
    if (reqType === "#LIST_GROUP") {
        document.getElementById("list").innerHTML = "";

        var groups = doc.getElementsByTagName("group")
        
        Array.from(groups).forEach(function(group) {
            var name = group.getAttribute("genre");
            var followers = group.getElementsByTagName("followers")[0].textContent;
            var imagePath = group.getElementsByTagName("imagePath")[0].textContent;

            followers = "Followers: " + followers;
            var infoarray = [followers];
            
            var button = document.createElement("button")

            button.innerHTML = name;
            button.setAttribute("onclick",'sendRequest(\"#LIST_STREAMS '+name+'\")');
            button.setAttribute("class","button");
            button.addEventListener('mouseover', showInfo(imagePath,infoarray,name));
            
            document.getElementById("list").appendChild(button);
        });
    }else if(reqType === "#LIST_STREAMS"){
        document.getElementById("list").innerHTML = "";

        var streamers = doc.getElementsByTagName("streamer")
        
        Array.from(streamers).forEach(function(stream) {
            var name = stream.getAttribute("name");
            var followers = stream.getElementsByTagName("followers")[0].textContent;
            followers = "Followers: " + followers;
            var contentType = stream.getElementsByTagName("content")[0].textContent;
            contentType = "Content: " + contentType;
            var language = stream.getElementsByTagName("language")[0].textContent;
            language = "Language: " + language;
            var imagePath = stream.getElementsByTagName("metadata")[0].getElementsByTagName("imagePath")[0].textContent;
            
            var infoarray = [followers,contentType,language];

            var button = document.createElement("button")

            button.innerHTML = name;
            button.setAttribute("onclick",'sendRequest(\"#SHOW_STREAM '+name+'\")');
            button.setAttribute("class","button");
            button.addEventListener('mouseover', showInfo(imagePath,infoarray,name));
            
            document.getElementById("list").appendChild(button);
        });
    }else if(reqType === "#SHOW_STREAM"){
        var stream = doc.getElementsByTagName("streamer")[0].getElementsByTagName("metadata")[0].getElementsByTagName("streamIp")[0].textContent;
        
        var video = document.getElementById("video");
        
        if (Hls.isSupported()) {
            var hls = new Hls();
            hls.loadSource(stream);
            hls.attachMedia(video);
        }else if (video.canPlayType("application/vnd.apple.mpegurl")) {
            video.src = stream;
        }
        showStream();
    }
};

ws.onopen = function(event) {
    console.log("Connected to server.");
    sendRequest("#LIST_GROUP");
};

ws.onerror = function(event) {
    console.log("Error: " + event.message);
    console.log("Error: " + event);
};

ws.onclose = function(event) {
    console.log("Connection closed.");
    ws.send("#EXIT");
};

window.onbeforeunload = function(e){
    ws.send("#EXIT");
};

//FUNCS
function sendRequest(req) {
    console.log("Sending request: " + req);
    ws.send(req);
    reqType = req.split(" ")[0];
}

function parseXML(xmlString) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xmlString, "text/xml");

    return xmlDoc;
}

function showInfo(imagePath,infos,name) {
    return function(){
        document.getElementById("text-container").style.display = "block";
        document.getElementById("video-container").style.display = "none";
        
        document.getElementById("infotitle").innerHTML = name;
        document.getElementById("pimage").src = imagePath;
        var textbox = "";

        infos.forEach(element => {
            textbox += element+ "<br>";
        });

        document.getElementById("text").innerHTML = textbox;
    }
}

function showStream(){
    document.getElementById("text-container").style.display = "none";
    document.getElementById("video-container").style.display = "block";
}

function waitForConnection(interval) {
    if (ws.readyState === 1) {
        return
    } else {
        setTimeout(function () {
            that.waitForConnection(interval);
        }, interval);
    }
};
//EXAMPLES
