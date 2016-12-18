function checkProductList() {
    var list_products_added = document.getElementById('list_products_added');
    var list_length = list_products_added.childElementCount;

    if(list_length > 2) {
        return true;
    }
}

function noProductAdded() {
    window.alert('Il est nécessaire d\'avoir au minimum deux produits dans votre liste.');
}

function generatePie() {
    var str = $('#select_feature option:selected').val();
    $("p").css("color","red");
    $("#test").append(  str +"</br>" );
    var person = new Object();

    $("#list_products_added .present").each(function(){
            var res = $(this).data(str);
            var mot = $(this).text();

        if ( res in person) {
            person[res] = person[res]+1
          //  $("#test").append(  "on creer pas " + res + "qui a la valeur " + person[res] + " </br>" );
        } else {
            person[res] = 1
           // $("#test").append(  "on creer " + res + "qui a la valeur " + person[res]+ " </br>" );
        }


    })
    var couleur =["#878BB6","#4ACAB4","#FF8153","#FFEA88"]
    var num_couleur = 0
    var labels = []
    var backgroundColor = []
    var donne = []
/*var pieData = []
    for (var i in person){
        $("#test").append( "il y a " + i + ": "+ person[i] + " </br>" );
        var tmp = {
            value : person[i],
            color : couleur[num_couleur]
        }
        pieData.push(tmp)
        num_couleur = num_couleur + 1
    }*/


    for (var i in person){
        $("#test").append( "il y a " + i + ": "+ person[i] + " </br>" );
       labels.push(i)
        backgroundColor.push(couleur[num_couleur])
        donne.push(person[i])
        num_couleur = num_couleur + 1
    }









    if(checkProductList()) {
       /* var pies = [
            {
                value: 20,
                color: "#878BB6"
            },
            {
                value: 40,
                color: "#4ACAB4"
            },
            {
                value: 10,
                color: "#FF8153"
            },
            {
                value: 30,
                color: "#FFEA88"
            }
        ];*/





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

}

function generateRadar() {

}