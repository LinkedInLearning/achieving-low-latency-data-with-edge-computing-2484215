<html>
<head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
    <script src="js/dashboard.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <script>
        //var source = new EventSource("/vital_rate");
        var source = new EventSource("/vitalStats");

        source.onmessage = function(event) {

            var vitalSign = JSON.parse(event.data);
            addVitalSign(vitalSign)
        };
    </script>
</head>
<body  style="background-color: black;color: white;">

    <div id="content">
        <table class="datatable">
            <tbody id="vitalTable">
                <#list model["vitalSigns"] as vs>
                <tr>
                    <td>${vs.name} <br/>
                    <img height="90px" width="90px" src="${vs.url}">
                    </td>
                    <#list vs.stats as stat>>
                            <td> <h3 class="heartHeader">${stat.label}</h3>
                                <canvas id="${stat.statName}Canvas${vs.id}" style="width:200px;background-color:black;"></canvas>
                            </td>
                    </#list>
                </tr>
            </tbody>
        </#list>
    </table>
    </div>

    <script id="chartScripts">
    var xValues = [00];
    var yValues = [7];

     <#list model["vitalSigns"] as vs>
     var heartRateChart${vs.id}=new Chart("heartRateCanvas${vs.id}", {
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
      options: {
        legend: {display: false},
        scales: {
          yAxes: [{ticks: {min: 0, max:150}}],
        }
      }
    });

    var bodyTemperatureChart${vs.id}=new Chart("bodyTemperatureCanvas${vs.id}", {
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
      options: {
        legend: {display: false},
        scales: {
          yAxes: [{ticks: {min: 0, max:150}}],
        }
      }
    });
     </#list>
    </script>

<!--    <button onclick="addOne()">Add one</button>-->
</body>
</html>