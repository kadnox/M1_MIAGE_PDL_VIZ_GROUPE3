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
    if(checkProductList()) {
        var pieData = [
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
        ];

        //Partie de redimensionnement obligatoire pour le responsive
        var parent = document.getElementById('canvas_container');
        parent.style.height = "500px";

        var canvas = document.getElementById("charts");
        var ctx = canvas.getContext("2d");

        canvas.width = parent.offsetWidth;
        canvas.height = parent.offsetHeight;

        new Chart(ctx).Pie(pieData);
    } else {
        noProductAdded();
    }
}

function generateBar() {

}

function generateRadar() {

}