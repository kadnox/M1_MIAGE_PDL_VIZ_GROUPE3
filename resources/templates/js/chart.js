
var couleur =["#878BB6","#4ACAB4","#FF8153","#FFEA88"]

function checkProductList() {
    var list_products_added = document.getElementById('list_products_added');
    var list_length = list_products_added.childElementCount;

    if(list_length > 2) {
        return true;
    }
}



function compareNombres(a, b) {
    return a - b;
}
function noProductAdded() {
    window.alert('Il est nécessaire d\'avoir au minimum deux produits dans votre liste.');
}

function generatePie() {
    var str = $('#select_feature option:selected').val();
    console.log(str)

    var tabl = new Object();

    $("#list_products_added .present").each(function(){
        var res = $(this).data(str);
        var mot = $(this).text();
        console.log(res + " et mot " + mot )

        if ( res in tabl) {
            tabl[res] = tabl[res]+1
            //  $("#test").append(  "on creer pas " + res + "qui a la valeur " + person[res] + " </br>" );
        } else {
            tabl[res] = 1
            // $("#test").append(  "on creer " + res + "qui a la valeur " + person[res]+ " </br>" );
        }


    })

    var num_couleur = 0
    var labels = []
    var backgroundColor = []
    var donne = []



    for (var i in tabl){
        $("#test").append( "il y a " + i + ": "+ tabl[i] + " </br>" );
        labels.push(i)
        backgroundColor.push(couleur[num_couleur])
        donne.push(tabl[i])
        num_couleur = num_couleur + 1
    }

    if(checkProductList()) {
        //Partie de redimensionnement obligatoire pour le responsive
        var parent = document.getElementById('canvas_container');
        parent.style.height = "500px";

        var canvas = document.getElementById("charts");
        var ctx = canvas.getContext("2d");


        canvas.width = parent.offsetWidth;
        canvas.height = parent.offsetHeight;

        var mycharto = new Chart(ctx,{
                type : "pie",
                data : {
                    labels : labels,
                    datasets : [
                        {
                            backgroundColor: backgroundColor,
                            data : donne
                        }
                    ]
                }
            }
        );

        // new Chart(ctx).Pie(pieData);

    } else {
        noProductAdded();
    }
}

function generateBar() {

    var str = $('#select_feature option:selected').val();
    var tabl = new Object();
    var tri = []

$("#list_products_added .present").each(function(){
        var res = $(this).data(str); // contient une valeur numérique comme une population
        var mot = $(this).text(); // nom du produit

        tri.push(mot)
        tabl[mot] = res
    })




    var donne = []


    tri.forEach(function(y) {

        var tmp = tabl[y]
        console.log(tmp +"valeur de i " + y)

        donne.push(tmp)

    });
    var parent = document.getElementById('canvas_container');
    parent.style.height = "500px";


    var canvas = document.getElementById("charts");
    var ctx = canvas.getContext("2d");


    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;



    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: tri,
            datasets: [{
                label: 'apples',
                data: donne,
                backgroundColor: "rgba(153,255,51,1)"
            }, ]
        }
    });
}

function generateRadar() {

}