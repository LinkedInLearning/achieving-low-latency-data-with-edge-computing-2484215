<html>
<head>
    <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
<!--    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.8.0/chart.js"></script>-->

    <script src="js/dashboard.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Edge Computing Vital Signs Showcase</title>
    <style>
    .datatable tbody tr td {padding: 10px;}
    .datatable {}

    .priority2
    {
       color: red;
       background-image: url("vitalImg/priority2.jpg");
         background-repeat: no-repeat;
         background-repeat: no-repeat;
    }

    .priority1
    {
       color: orange;
       background-image: url("vitalImg/priority1.jpg");
       background-repeat: no-repeat;
    }
    .priority0
    {
       color: green;
       background-image: url("vitalImg/priority0.jpg");
       background-repeat: no-repeat;
    }

    .heartHeader {
        font-size: 8pt;
        text-align: center;
    }

    .patientSummary {
        background-color: white;
        color:black;
        text-align: center;
        align-content: center
    }

    .patientSummary img {
     height: 70px;
     width: 70px;
    }

    .title
    {
        padding-left: 30%;
        background-color: #eeeeee;
        color: black;
    }

    .title img
    {
        width: 15px;
        height: 15px;
    }

    .title span
    {
        padding-right: 40px;
    }
    </style>

    <script>
        var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/topic/vitalSigns', function (vitalSignResponse) {

                var vitalSign = JSON.parse(vitalSignResponse.body);
                addVitalSign(vitalSign)
        });
    });



    </script>


    <script>
            //SSE for conditional summaries
          /*  var vitalSummaryEventsSource = new EventSource("vitalSummaryEvents-sse");
            vitalSummaryEventsSource.onmessage = function(event) {

            var countSummaries = JSON.parse(event.data)
            var lowHtmlPanel = document.getElementById("lowPriorityCount");
            lowHtmlPanel.innerHTML = countSummaries.lowCount;

            var warningHtmlPanel = document.getElementById("warningPriorityCount");
            warningHtmlPanel.innerHTML = countSummaries.warningCount;

             var severeHtmlPanel = document.getElementById("severePriorityCount");
            severeHtmlPanel.innerHTML = countSummaries.severeCount;
         }*/


            //-------------------------------
            // streamVitalOverviewEvents-sse
             var vitalOverviewEventsSource = new EventSource("streamVitalOverviewEvents-sse");

            vitalOverviewEventsSource.onmessage = function(event) {

                    var vitalOverviews = JSON.parse(event.data)

                    for (let i in vitalOverviews) {
                          var panel = document.getElementById(vitalOverviews[i].id+"Priority");

                          if(panel == null)
                          {
                                console.log("ERROR: NOT FOUND vitalOverviews id="+vitalOverviews[i].id+"Priority");
                                return;
                          }

                           panel.innerHTML = "<p class='priority"+vitalOverviews[i].priority+"'>&nbsp;</p>";
                        }
              }
    </script>
</head>
<body  style="background-color: black;color: white;padding-left: 30px;">
    <div class="title">
            <span>
                Edge - Remote Patient Monitoring
<!--                <strong id="lowPriorityCount">0</strong> <img src="vitalImg/green-circle.jpg">-->
<!--                <strong id="warningPriorityCount">0</strong> <img src="vitalImg/orange-circle.jpg">-->
<!--                <strong id="severePriorityCount">0</strong> <img src="vitalImg/red-circle.jpg">-->
<!--                <a href="/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config">Swagger Testing</a>-->
            </span>
    </div>
    <div id="content">
        <table class="datatable">
            <tbody id="vitalTable">
                <#list model["vitalSigns"] as vs>
                <tr>
                    <td class="patientSummary">
                        <img src="${vs.url}"> <br/>
                            ${vs.name}
                        <p id="${vs.id}Priority"></p>
                        </td>
                        <#list vs.stats as stat>
                                <td> <h3 class="heartHeader">${stat.label}</h3>
                                    <canvas id="${stat.statName}Canvas${vs.id}" style="width:300px;background-color:black;"></canvas>
                                </td>
                        </#list>
                </tr>
            </tbody>
        </#list>
    </table>
    </div>

    <script id="chartScripts">
    var xValues = [];
    var yValues = [];

     <#list model["vitalSigns"] as vs>
        <#list vs.stats as stat>
             var ${stat.statName}Chart${vs.id}=new Chart("${stat.statName}Canvas${vs.id}", {
                  type: "line",
                  data: {
                    labels: xValues,
                    datasets: [{
                      fill: false,
                      lineTension: 0,
                      backgroundColor: "rgba(255,0,0,1.0)",
                      borderColor: "rgba(255,0,0,0.5)",
                      data: yValues
                    }]
                  },
                  maintainAspectRatio: false,
                  options: {
                    legend: {display: false},
                    scales: {
                      yAxes: [{ticks: {min: ${stat.min}, max:${stat.max}}}],
                    }
                  }
                });
        </#list>
     </#list>
    </script>

<!--    <button onclick="addOne()">Add one</button>-->
</body>
</html>