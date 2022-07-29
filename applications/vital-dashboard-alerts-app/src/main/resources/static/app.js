var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("<tr><td>Name</td><td>Stat</td><td>Value</td><td>Latency(ms)</td><td>Time</td></tr>");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/alerts', function (vitalAlert) {

                var vitalAlertJson = JSON.parse(vitalAlert.body);
                addVitalAlert(vitalAlertJson);
                //showGreeting("ALERT: Check "+vitalAlertJson.vitalSummary.vital.name+" "+vitalAlertJson.vitalStat.statName+"="+vitalAlertJson.vitalStat.value+" time="+vitalAlertJson.vitalStat.timestamp);
        });

    });

}

function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/events", {}, JSON.stringify({'name': $("#name").val()}));
}

function updateOverviewPanel(vitalAlert)
{
   var vitalId = vitalAlert.vitalSummary.vital.id;
   var vitalRowId = "vitalRow"+vitalId;

   var vitalRow = document.getElementById(vitalRowId);

   var priority =vitalAlert.vitalSummary.vitalOverview.priority;

    var new_row = "<td id=\""+vitalRowId+"\"><img src='vitalImg/priority"+priority+".jpg'/><span class='numberCircle'>"+vitalAlert.vitalSummary.vitalOverview.alarmCount+"</span><br/><img class='vitalImg' src='vitalImg/"+vitalId+".jpg'/><br/>" + vitalAlert.vitalSummary.vital.name + "</td>";

   if(vitalRow == null)
   {
      $("#vitalOverviews").append(new_row);
   }
   else
   {
      $("#"+vitalRowId).replaceWith(new_row);
   }

}//---------------------------------------------

function addVitalAlert(vitalAlertJson) {

  var eventTime = new Date(vitalAlertJson.vitalStat.timestamp);
  var nowTime = new Date();
  var timeDiff = nowTime - eventTime; //in ms

   updateOverviewPanel(vitalAlertJson);
   var rowHtml = "<tr><td>"+vitalAlertJson.vitalSummary.vital.name+"</td><td>"+vitalAlertJson.vitalStat.statName+"</td><td>"+vitalAlertJson.vitalStat.value+"</td><td>"+timeDiff+"ms</td><td>"+eventTime+"</td></tr>";
    $("#greetings").append(rowHtml);
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

