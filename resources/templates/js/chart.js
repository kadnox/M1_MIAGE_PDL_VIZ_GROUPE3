var couleur = ["#FFEA88", "#E67E30", "#7FDD4C", "#79F8F8", "#912B3B", "#F0C300", "#884DA7", "#5A5E6B", "#FFCB60", "#1E7FCB", "#878BB6", "#4ACAB4", "#FF8153", "#AE642D", "#D1B606", "#8B6C42", "#C8AD7F", "#AFA77B", "#766F64", "#FFE4C4", "#4E3D28", "#FFFFFF", "#0000FF", "#3A8EBA", "#686F8C", "#1E7FCB", "#5472AE", " #048B9A", "#26C4EC", "#357AB7", "#8EA2C6", "#22427C", "#24445C", "#318CE7", "#003366", "#24445C", "#00CCCB", " #DFF2FF", "#2C75FF", "#BBD2E1", "#80D0D0", "#56739A", "#24445C", "#21177D", "#9683EC", "#03224C", "#0F056B", "#067790", "#6600FF", "#1D4851", "#318CE7", "#0131B4", "#008E8E", "#425B8A", "#25FDE9", "#E2BC74", "#6D071A", "#6B0D0D", "#FCDC12", "#5B3C11", "#842E1B", "#3F2204", " #CD853", "#CDCD0D", "#614B3A", "#26C4EC", "#462E01", "#FF5E4D", "#7E3300", "#960018", "#FEC3AC", "#F4661B", "#3A020D", "#83A697", "#BB0B0B", "#357AB7", "#FEC3AC", "#D0C07A", "#FBF2B7", "#000010", "#806D5A", "#85530F", "#FEFEFE", "#5A3A22", "#DB1702", "#F7FF3C", "#DF6D14", "#B9B276", "#960018", "#FF69B4"]
var mycharto = true; // indique qu'il n y a jamais eu de graphique variable globale qui conduit le graphique

function checkProductList(nb_product) {
    var list_products_added = document.getElementById('list_products_added');
    var list_length = list_products_added.childElementCount;

    if (list_length > nb_product) {
        return true;
    }
}

function compareNombres(a, b) {
    return a - b; // permet de trier les nombres par ordre croissant util pour les dates
}

function noProductAdded(nb_product) { // fixer a 2 pour bar, line , pie et a 3 pour polarArea et Radar car ils sont en 3 dimensions
    window.alert('Il est nécessaire d\'avoir au minimum ' + nb_product + " produits dans votre liste.");
}

function generatePie() {
    var str = $('#select_feature option:selected').val();
    var type_feature = $('#select_feature option:selected').data("charts")
    var nom_feature = $('#select_feature option:selected').text();
    var tabl = new Object();
    if (type_feature == "percentCent") {
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str);
            var mot = $(this).text(); // ici il ne s'agit plus d'un mot mais  d'un pourcentage
            if (res) {
                tabl[res] = mot;
            }
            else {
                $(this).parent().addClass("disabled"); // on grise le produit qui n' a pas servi.
            }
        })
        titre = "pourcentage d'apparition " + nom_feature
    }
    else { // sinon on compte le nombre d'apparition d'une occurence
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str);
            var mot = $(this).text();
            if (res) {

                if (res in tabl) {
                    tabl[res] = tabl[res] + 1
                } else {
                    tabl[res] = 1
                }

            }
            else {
                $(this).parent().addClass("disabled"); // on grisse le produit qui n' a pas servi.
            }
        })
        var titre = "repartition en fonction de " + nom_feature;
    }

    var num_couleur = 0
    var labels = []
    var backgroundColor = []
    var donne = []


    for (var i in tabl) {
        $("#test").append("il y a " + i + ": " + tabl[i] + " </br>");
        labels.push(i)
        backgroundColor.push(couleur[num_couleur])
        donne.push(tabl[i])
        num_couleur = num_couleur + 1
    }

    if (checkProductList(2)) {
        if (mycharto != true) {
            mycharto.destroy()// détruit le graphique precedent
        }
        //Partie de redimensionnement obligatoire pour le responsive
        var parent = document.getElementById('canvas_container');
        parent.style.height = "500px";

        var canvas = document.getElementById("charts");

        var ctx = canvas.getContext("2d");


        canvas.width = parent.offsetWidth;
        canvas.height = parent.offsetHeight;

        mycharto = new Chart(ctx, {
                type: "pie",
                data: {
                    labels: labels,
                    datasets: [
                        {
                            backgroundColor: backgroundColor,
                            data: donne
                        }
                    ]
                },
                options: {
                    title: {
                        display: true,
                        text: titre
                    }
                }
            }
        );

      } else {
        noProductAdded(2);
    }
}

function generateBar() {

    var str = $('#select_feature option:selected').val();
    var nom_feature = $('#select_feature option:selected').text();
    var tabl = new Object();
    var tri = []
    var type_feature = $('#select_feature option:selected').data("chart");
    var donne = []
    var titre = "";
    if (type_feature == "integer") {
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str); // contient une valeur numérique comme une population
            var mot = $(this).text(); // nom du produit
            if (res) {
                tri.push(mot)
                tabl[mot] = res
            }
            else {
                $(this).parent().addClass("disabled");
            }
        })
        tri.forEach(function (y) {
            var tmp = tabl[y]
            donne.push(tmp)
        });
        titre = nom_feature + " pour chaque produit";
    }
    else {
        // sinon on compte le nombre d'apparition d'une occurence
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str);
            var mot = $(this).text();
            if (res) {
                if (res in tabl) {
                    tabl[res] = tabl[res] + 1
                } else {
                    tabl[res] = 1
                }
            }
            else {
                $(this).parent().addClass("disabled"); // on grisse le produit qui n' a pas servi.
            }
        })

        for (var i in tabl) {
            tri.push(i)
            donne.push(tabl[i])
        }
        titre = " Nombre d'apparation de la caractéristique" + nom_feature + " en fonction de sa valeur dans la liste des produits"
    }

    var parent = document.getElementById('canvas_container');
    parent.style.height = "500px";

    var canvas = document.getElementById("charts");
    var ctx = canvas.getContext("2d");

    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;
    if (checkProductList(2)) {
        if (mycharto != true) {
            mycharto.destroy();
        }

        mycharto = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: tri,
                datasets: [{
                    label: nom_feature,
                    data: donne,
                    backgroundColor: "rgba(30,127,203,1)"

                },]
            },
            options: {
                title: {
                    display: true,
                    text: titre
                }
            }
        });
    }
    else {
        noProductAdded(2);
    }
}

function generateRadar() {
    var str = $('#select_feature option:selected').val();
    var nom_feature = $('#select_feature option:selected').text();
    var tabl = new Object();
    var tri = []
    var type_feature = $('#select_feature option:selected').data("chart");
    var donne = []
    var titre = "";

    if (type_feature == "integer") {
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str); // contient une valeur numérique comme une population
            var mot = $(this).text(); // nom du produit
            if (res) {
                tri.push(mot)
                tabl[mot] = res
            }
            else {
                $(this).parent().addClass("disabled");
            }

        })
        tri.forEach(function (y) {
            var tmp = tabl[y]
            donne.push(tmp)
        });
        titre = nom_feature + " pour chaque produit";
    }
    else {
        // sinon on compte le nombre d'apparition d'une occurence
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str);
            var mot = $(this).text();
            if (res) {

                if (res in tabl) {
                    tabl[res] = tabl[res] + 1
                } else {
                    tabl[res] = 1
                }

            }
            else {
                $(this).parent().addClass("disabled"); // on grisse le produit qui n' a pas servi.
            }
        })

        for (var i in tabl) {
            tri.push(i)
            donne.push(tabl[i])
        }
        titre = " Nombre d'apparation de la caractéristique" + nom_feature + " en fonction de sa valeur dans la liste des produits"
    }
    var parent = document.getElementById('canvas_container');
    parent.style.height = "500px";

    var canvas = document.getElementById("charts");
    var ctx = canvas.getContext("2d");

    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;
    if (checkProductList(3)) {
        if (mycharto != true) {
            mycharto.destroy();
        }

        mycharto = new Chart(ctx, {
            type: 'radar',
            data: {
                labels: tri,
                datasets: [{
                    label: nom_feature,
                    data: donne,
                    backgroundColor: "rgba(30,127,203,1)"

                },]
            },
            options: {
                title: {
                    display: true,
                    text: titre
                }
            }
        });
    }
    else {
        noProductAdded(3);
    }

}

function generateLine() {
    var str = $('#select_feature option:selected').val();
    var nom_feature = $('#select_feature option:selected').text();
    var tabl = new Object();
    var tri = []
    var type_feature = $('#select_feature option:selected').data("chart");
    var donne = []
    var titre = "";

    if (type_feature == "integer") {
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str); // contient une valeur numérique comme une population
            var mot = $(this).text(); // nom du produit
            if (res) {
                tri.push(mot)
                tabl[mot] = res
            }
            else {
                $(this).parent().addClass("disabled");
            }

        })
        tri.forEach(function (y) {

            var tmp = tabl[y]


            donne.push(tmp)

        });
        titre = nom_feature + " pour chaque produit";
    }
    else {
        // très peu d'utilité  puisqu'on n'offre pas la possibilité de l'afficher pour autre choses que des entiers; si jamais l'utilisateur utilise un moyen d'y acceder affiche quand meme quelque chose
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str);
            var mot = $(this).text();
            if (res) {

                if (res in tabl) {
                    tabl[res] = tabl[res] + 1
                } else {
                    tabl[res] = 1
                }
            }
            else {
                $(this).parent().addClass("disabled"); // on grise le produit qui n' a pas servi.
            }
        })

        for (var i in tabl) {
            tri.push(i)
            donne.push(tabl[i])
        }
        titre = " Nombre d'apparation de la caractéristique" + nom_feature + " en fonction de sa valeur dans la liste des produits"
    }
    var parent = document.getElementById('canvas_container');
    parent.style.height = "500px";

    var canvas = document.getElementById("charts");
    var ctx = canvas.getContext("2d");

    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;
    if (checkProductList(2)) {
        if (mycharto != true) {
            mycharto.destroy();
        }

        mycharto = new Chart(ctx, {
            type: 'line',
            data: {
                labels: tri,
                datasets: [{
                    label: nom_feature,
                    data: donne,
                    backgroundColor: "rgba(30,127,203,1)"

                },]
            },
            options: {
                title: {
                    display: true,
                    text: titre
                }
            }
        });
    }
    else {
        noProductAdded(2);
    }
}

function generatePolar() {
    var str = $('#select_feature option:selected').val();
    var nom_feature = $('#select_feature option:selected').text();
    var tabl = new Object();
    var tri = []
    var type_feature = $('#select_feature option:selected').data("chart");
    var donne = []
    var titre = "";
    if (type_feature == "integer") {
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str); // contient une valeur numérique comme une population
            var mot = $(this).text(); // nom du produit
            if (res) {
                tri.push(mot)
                tabl[mot] = res
            }
            else {
                $(this).parent().addClass("disabled");
            }

        })
        tri.forEach(function (y) {
            var tmp = tabl[y]
            donne.push(tmp)
        });
        titre = nom_feature + " pour chaque produit";
    }
    else {
        // sinon on compte le nombre d'apparition d'une occurence
        $("#list_products_added .present").each(function () {
            var res = $(this).data(str);
            var mot = $(this).text();
            if (res) {
                if (res in tabl) {
                    tabl[res] = tabl[res] + 1
                } else {
                    tabl[res] = 1
                }
            }
            else {
                $(this).parent().addClass("disabled"); // on grisse le produit qui n' a pas servi.
            }
        })

        for (var i in tabl) {
            tri.push(i)
            donne.push(tabl[i])
        }
        titre = " Nombre d'apparation de la caractéristique" + nom_feature + " en fonction de sa valeur dans la liste des produits"
    }
    var parent = document.getElementById('canvas_container');
    parent.style.height = "500px";

    var canvas = document.getElementById("charts");
    var ctx = canvas.getContext("2d");

    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;
    if (checkProductList(3)) {
        if (mycharto != true) {
            mycharto.destroy();
        }

        mycharto = new Chart(ctx, {
            type: 'polarArea',
            data: {
                labels: tri,
                datasets: [{
                    label: nom_feature,
                    data: donne,
                    backgroundColor: couleur

                },]
            },
            options: {
                title: {
                    display: true,
                    text: titre
                }
            }
        });
    }
    else {
        noProductAdded(3);
    }
}