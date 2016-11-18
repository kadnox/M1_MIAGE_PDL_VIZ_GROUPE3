function generatePie() {
	var pieData = [
        {
            value: 20,
            color:"#878BB6"
        },
        {
            value : 40,
            color : "#4ACAB4"
        },
        {
            value : 10,
            color : "#FF8153"
        },
        {
            value : 30,
            color : "#FFEA88"
        }
    ];
    
    var charts = document.getElementById("charts").getContext("2d");
    new Chart(charts).Pie(pieData);
}