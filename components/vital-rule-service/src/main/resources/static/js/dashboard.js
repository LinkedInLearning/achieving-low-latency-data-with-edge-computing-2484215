
var MAX_CHART_POINTS = 10;

function  drawVitalSigns(vitalSign)
{
    var html = "<tr>"+
                    "<td>"+vitalSign.name+"</td>"+
                    "<td> <h3 class=\"heartHeader\">Heart Rate</h3>"+
                        "<canvas id=\"heartRateCanvas"+vitalSign.vitalId+"\" style=\"width:200px;background-color:black;\"></canvas>"+
                    "</td>"+
                    "<td>"+
                        "<h3 class=\"heartHeader\">Body Temperate</h3>"+
                        "<canvas id=\"bodyTemperatureCanvas"+vitalSign.vitalId+"\" style=\"width:200px;background-color:black;\"></canvas>"+
                    "</td>"+
                "</tr>";

   var vitalTable = document.getElementById("vitalTable");
   vitalTable.innerHTML += html;

    //Build Heart Rate
   if(typeof window["heartRateCanvasChart"+vitalSign.vitalId] != 'undefined')
    {
        return;
    }

    constructChart("heartRateCanvas"+vitalSign.vitalId);
    constructChart("bodyTemperatureCanvas"+vitalSign.vitalId);

     //Heart rate
      //window["heartRateCanvasChart"+vitalSign.id] = constructChart("heartRateCanvas"+vitalSign.id);
      //window["bodyTemperatureCanvasChart"+vitalSign.id] = constructChart("bodyTemperatureCanvas"+vitalSign.id);

}//---------------------------------
function constructChart(id)
{
  var xValues = [00];
  var yValues = [7];

  window["heartRateCanvasChart"+id] =new Chart("heartRateCanvas1", {
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

  window["bodyTemperateCanvasChart"+id]=new Chart("bodyTemperatureCanvas1", {
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
}
function constructChartBackup(id)
{
    var xValues = ["Hi"];
     var yValues = [7];

     var ctx = document.getElementById(id).getContext("2d");
          var chart = new Chart(ctx, {
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
//     chart.update();

     return chart;
}
function draw()
{
    var drawX = [1,2];
    var drawY = [1,2];

    heartRateChart.data.labels = drawX;
    heartRateChart.data.datasets[0].data = drawY;
    heartRateChart.update();
}

function addVitalSign(vitalSign)
{
    addData(window[vitalSign.statName+"Chart"+vitalSign.vitalId],vitalSign.label,vitalSign.value);
//    addData(window["bodyTemperateChart1"],"",vitalSign.bodyTemperature);
}

function addOne()
{
    addData(window["heartRateChart1"],"Label1",99);
}

function addData(chart, label, data)
{
    if(chart.data.labels.length > MAX_CHART_POINTS)
    {
        removeFirstData(chart);
    }

    chart.data.labels.push(label);

    chart.data.datasets[0].data.push(data);

//    chart.data.datasets.forEach((dataset) => {
//        dataset.data.push(data);
//    });
    chart.update();
}
function removeFirstData(chart) {
    chart.data.labels.shift();
    chart.data.datasets.forEach((dataset) => {
        dataset.data.shift();
    });
    chart.update();
}

function removeData(chart) {
    chart.data.labels.pop();
    chart.data.datasets.forEach((dataset) => {
        dataset.data.pop();
    });
    chart.update();
}