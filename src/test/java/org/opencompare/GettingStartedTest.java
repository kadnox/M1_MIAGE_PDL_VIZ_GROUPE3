package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.CSVExporter;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by gbecan on 02/12/15.
 */
public class GettingStartedTest {

    @Test
    public void testGettingStarted() throws IOException {

        // Define a file representing a PCM to load
        File pcmFile = new File("pcms/example.pcm");


        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        // Load the file
        // A loader may return multiple PCM containers depending on the input format
        // A PCM container encapsulates a PCM and its associated metadata
        List<PCMContainer> pcmContainers = loader.load(pcmFile);

        //Structure de données pour le rangement des données
        Map ht = new Hashtable<String, Map<String, String>>();

        for (PCMContainer pcmContainer : pcmContainers) {

            // Get the PCM
            PCM pcm = pcmContainer.getPcm();

            // Browse the cells of the PCM
            for (Product product : pcm.getProducts()) {
                //On crée le hashtable contenu dans la liste
                Hashtable values = new Hashtable<String, String>();

                for (Feature feature : pcm.getConcreteFeatures()) {
                    // Find the cell corresponding to the current feature and product
                    Cell cell = product.findCell(feature);

                    // Get information contained in the cell
                    String content = cell.getContent();

                    //On insére la clé et la valeur
                    values.put(feature.getName(), content);
                }

                //On insère la clé et la valeur (L'arrayList) dans le hashtable
                ht.put(product.getKeyContent(), values);
            }

            //On récupère les clés
            Set<String> keys = ht.keySet();
            Iterator<String> it1 = keys.iterator();

            // on crée un tableau contenant tout les produits
            int nb_produit = keys.size();
            String liste_produit[] = new String[nb_produit];

            int compteur_produit =0;

            //Tant que l'on peut itérer
            while (it1.hasNext()) {
                //On récupère la clé
                String key = it1.next();
                //On récupère la valeur de cette clé
                Hashtable<String, String> values = (Hashtable)ht.get(key);

                //On affiche
                System.out.println("Key : " + key);
                liste_produit[compteur_produit]=key;
                compteur_produit++;
                Set<String> keysValues = values.keySet();
                Iterator<String> it2 = keysValues.iterator();

                while (it2.hasNext()) {
                    //On récupère la clé
                    String keyV = it2.next();
                    //On récupère la valeur de la clé
                    String valueV = values.get(keyV);



                    //On affiche
                    System.out.println("\t" + keyV + " : " + valueV);
                }


            }

            // Export the PCM container to CSV
            CSVExporter csvExporter = new CSVExporter();
            String csv = csvExporter.export(pcmContainer);

            // Write CSV content to file
            Path outputFile = Files.createTempFile("oc-", ".csv");
            Files.write(outputFile, csv.getBytes());
            System.out.println("PCM exported to " + outputFile);


            System.out.println("nombre de produit" + liste_produit.length + " numero variable" + nb_produit);

            for( int i = 0; i<liste_produit.length;i++){
                System.out.println("le produit numero " + i +" est " + liste_produit[i]);

            }
            Map test =(Map)ht.get(liste_produit[0]);
            Set<String> ensemble_feature= test.keySet();
            System.out.println(ensemble_feature.size());
            Object [] liste_feature=  ensemble_feature.toArray();

            for(int i =0; i<liste_feature.length;i++){
                System.out.println("la feature numero " + i + " est " + liste_feature[i]);
            }




            String newline = System.getProperty("line.separator");



            FileWriter writer = new FileWriter("src/test/files/resultat.html");
/*
            writer.write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                    "\t\t<link href=\"http://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n" +
                    "\t\t<link rel=\"stylesheet\" href=\"../../../../materialize/css/materialize.min.css\">\n" +
                    "\t\t<link rel=\"stylesheet\" href=\"style.css\">\n" +
                    "\t\t\t<script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-2.1.1.min.js\">\n" +
                    "\n" +
                    "</script>\n" +
                    "\t<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css\">\n" +
                    "\t <script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js\"></script>\n" +
                    "<script type=\"text/javascript\">\n" +
                    "\t\t$(document).ready(function() {\n" +
                    "\t\t\t$('select').material_select();\n" +
                    "\t\t});\n" +
                    "\t</script>\n" +
                    "\t\t<script type=\"text/javascript\">\n" +
                    "\t\t\tfunction searchProductToAdd() {\n" +
                    "\t\t\t\tvar ul = document.getElementById('toAdd');\n" +
                    "\t\t\t\tvar search = document.getElementById('search_product_to_add').value;\n" +
                    "\t\t\t\tvar search_length = search.length;\n" +
                    "\n" +
                    "\t\t\t\tconsole.log(search_length);\n" +
                    "\n" +
                    "\t\t\t\tfor(var i = 0, li; li = ul.children[i]; i++) {\n" +
                    "\t\t\t\t\tif(li.tagName == 'LI') {\n" +
                    "\t\t\t\t\t\tvar product_name = ul.children[i].getElementsByClassName('product_name')[0].innerHTML;\n" +
                    "\n" +
                    "\t\t\t\t\t\tif(product_name.substring(0, search_length) != search) {\n" +
                    "\n" +
                    "\t\t\t\t\t\t}\n" +
                    "\t\t\t\t  \t}\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t</script>\n" +
                    "\n" +
                    "\t\t<title>$title</title>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "\t\t<div class=\"row\" style=\"background-color: red\">\n" +
                    "\t\t\t<div class=\"col s12 m3 offset-m1 input-field\" style=\"background-color: pink\">\n" +
                    "\t\t\t\t<select id=\"feature\" multiple>\n" +

                    "\t\t\t\t\t<option value=\"0\" disabled selected>Select feature</option>\n" );

            //"\t\t\t\t\t<option value=\"1\">Feature 1</option>\n" +
            for(int i =1; i<liste_feature.length+1;i++){
                writer.write("<option value="+ i+"> "+ liste_feature[i-1] + "</option>" + newline);
            }
            writer.write( "\t\t\t\t</select>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t\t<div class=\"col s12 m7 offset-m1\">\n" +
                    "\t\t\t\t<div class=\"row\" style=\"background-color: blue\">\n" +
                    "\t\t\t\t\t<div class=\"col s5 center\" style=\"background-color: green\">\n" +
                    "\t\t\t\t\t\t<div class=\"search_bar\">\n" +
                    "\t\t\t\t\t\t\t<form class=\"input-field\">\n" +
                    "\t\t\t\t\t\t\t\t<input id=\"search_product_to_add\" type=\"search\" placeholder=\"Search...\" onKeyUp=\"searchProductToAdd()\">\n" +
                    "\t\t\t\t\t\t\t\t<label for=\"search\">\n" +
                    "\t\t\t\t\t\t\t\t\t<i class=\"material-icons\">search</i>\n" +
                    "\t\t\t\t\t\t\t\t</label>\n" +
                    "\t          \t\t\t\t</form>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\n" +
                    "\t\t\t\t\t\t<div class=\"list_product_to_add\" style=\"background-color: white\">\n" +
                    "\t\t\t\t\t\t\t<ul id=\"toAdd\" class=\"collection\">\n" );

            for(int i = 0; i<22; i++){

                Map temporaire =(Map)ht.get(liste_produit[i]); //
                String ligne_html = "<div class='product_name' ";
                for (int j =0 ; j<liste_feature.length; j++){
                    String nom_feature= (String)liste_feature[j];
                    nom_feature= nom_feature.toLowerCase();
                    nom_feature = nom_feature.replace(" ", "_");
                    String data_tmp = (String)temporaire.get(liste_feature[j]);
                    ligne_html = ligne_html + "data-" + nom_feature + "='"+ data_tmp + "' ";
                }


                writer.write("<li class=\"collection-item\">" +newline);

                writer.write(ligne_html +">"+ liste_produit[i] +"</div>"+ newline );
                writer.write("<a href='#!' class='secondary-content'><i class='material-icons'>keyboard_arrow_right</i></a>"+ newline +"</li>");


            }

            writer.write(
                    "\t\t\t\t\t\t\t</ul>\n" +
                            "\t\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t\n" +
                            "\t\t\t\t\t<div class=\"col s2 center\" style=\"background-color: red\">\n" +
                            "\t\t\t\t\t\t<div class=\"arrows\">\n" +
                            "\t\t\t\t\t\t\t<ul>\n" +
                            "\t\t\t\t\t\t\t\t<li>\n" +
                            "\t\t\t\t\t\t\t\t\t<a href=\"#!\" class=\"secondary-content\"><i class=\"material-icons\">fast_forward</i></a>\n" +
                            "\t\t\t\t\t\t\t\t</li>\n" +
                            "\t\t\t\t\t\t\t\t<li>\n" +
                            "\t\t\t\t\t\t\t\t\t<a href=\"#!\" class=\"secondary-content\"><i class=\"material-icons\">fast_rewind</i></a>\n" +
                            "\t\t\t\t\t\t\t\t</li>\n" +
                            "\t\t\t\t\t\t\t</ul>\n" +
                            "\t\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\n" +
                            "\t\t\t\t\t<div class=\"col s5 center\" style=\"background-color: yellow\">\n" +
                            "\t\t\t\t\t\t<div class=\"search_bar\">\n" +
                            "\t\t\t\t\t\t\t<form class=\"input-field\">\n" +
                            "\t\t\t\t\t\t\t\t<input id=\"search_product_to_remove\" type=\"search\" placeholder=\"Search...\" onKeyUp=\"search_product_to_remove()\">\n" +
                            "\t\t\t\t\t\t\t\t<label for=\"search\">\n" +
                            "\t\t\t\t\t\t\t\t\t<i class=\"material-icons\">search</i>\n" +
                            "\t\t\t\t\t\t\t\t</label>\n" +
                            "\t          \t\t\t\t</form>\n" +
                            "\t\t\t\t\t\t</div>\n" +
                            "\n" +
                            "\t\t\t\t\t\t<div class=\"list_product_view\" style=\"background-color: white\">\n" +
                            "\t\t\t\t\t\t\t<ul id=\"toremove\" class=\"collection\">\n" );
            for (int i= 23; i<43;i++){
                writer.write("<li class='collection-item'>" + liste_produit[i] + newline);
                writer.write("<a href=\"#!\" class=\"secondary-content left\"><i class=\"material-icons\">keyboard_arrow_left</i></a>" + newline + "</li>" + newline);
            }

            writer.write(  "\t\t\t\t\t\t\t</ul>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t\t<div class=\"col s6 offset-s6\">\n" +
                    "\t\t\t\t$chart\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\t\t<div class=\"row\">\n" +
                    "\t\t\t<div class=\"col s12\" style=\"background-color: pink\">\n" +
                    "\t\t\t\taa\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\t</body>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\t\n" +
                    "</html>\n");

*/
            writer.write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                    "\t\t<link href=\"http://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n" +
                    "  \t\t<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css\">\n" +
                    "\t\t<link rel=\"stylesheet\" href=\"style.css\">\n" +
                    "\t\t\n" +
                    "\t\t<script type=\"text/javascript\" src=\"userConfig.js\"></script>\n" +
                    "\n" +
                    "\t\t<title>PCM Example </title>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "\t\t<div id=\"research\" class=\"row\">\n" +
                    "\t\t\t<div id=\"feature\" class=\"col s12 m12 l3 offset-l1 input-field\">\n" +
                    "\t\t\t\t<select id=\"select_feature\">\n" +
                    "\t\t\t\t\t<option value=\"0\" disabled selected>Select feature</option>");

            for(int i =1; i<liste_feature.length+1;i++){
                writer.write("<option value="+ i+"> "+ liste_feature[i-1] + "</option>" + newline);
            }

            writer.write("</select>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t\t<div id=\"product\" class=\"col s12 m12 l7 offset-l1\">\n" +
                    "\t\t\t\t<div class=\"col s12 m5 center\">\n" +
                    "\t\t\t\t\t<div class=\"search_bar\">\n" +
                    "\t\t\t\t\t\t<form class=\"input-field card\">\n" +
                    "\t\t\t\t\t\t\t<input id=\"search_product_to_add\" type=\"search\" placeholder=\"Rechercher...\" onKeyUp=\"searchProductToAdd()\">\n" +
                    "\t\t\t\t\t\t\t<label for=\"search\">\n" +
                    "\t\t\t\t\t\t\t\t<i class=\"material-icons\">search</i>\n" +
                    "\t\t\t\t\t\t\t</label>\n" +
                    "          \t\t\t\t</form>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\n" +
                    "\t\t\t\t\t<div>\n" +
                    "\t\t\t\t\t\t<ul id=\"list_products_to_add\" class=\"collection\">\n" +
                    "\t\t\t\t\t\t\t<li class=\"collection-item no_result hide\">\n" +
                    "\t\t\t\t\t\t\t\t<span class=\"product_name\">Aucun résultat</span>\n" +
                    "\t\t\t\t\t\t\t</li>");

            for(int i = 0; i<liste_produit.length; i++){

                writer.write("<li class=\"collection-item\">");
                Map temporaire =(Map)ht.get(liste_produit[i]); //
                String ligne_html = "<span class='product_name' ";
                for (int j =0 ; j<liste_feature.length; j++){
                    String nom_feature= (String)liste_feature[j];
                    nom_feature= nom_feature.toLowerCase();
                    nom_feature = nom_feature.replace(" ", "_");
                    String data_tmp = (String)temporaire.get(liste_feature[j]);
                    ligne_html = ligne_html + "data-" + nom_feature + "='"+ data_tmp + "' ";
                }




                writer.write(ligne_html +">"+ liste_produit[i] +"</span>"+ newline );
                writer.write("<a class=\"secondary-content\" data-name="+ liste_produit[i] +" onClick=\"addProduct(this)\">\n" +
                        "\t\t\t\t\t\t\t\t\t<i class=\"material-icons\">keyboard_arrow_right</i>\n" +
                        "\t\t\t\t\t\t\t\t</a>\n" +
                        "\t\t\t\t\t\t\t</li>");



            }
            writer.write("</ul>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t\t<div id=\"arrows\" class=\"col s12 m2 center\">\n" +
                    "\t\t\t\t\t<ul id=\"ul_arrows\">\n" +
                    "\t\t\t\t\t\t<li id=\"addAllButton\">\n" +
                    "\t\t\t\t\t\t\t<a class=\"secondary-content\" onClick=\"addAllProduct()\"><i class=\"material-icons\">fast_forward</i></a>\n" +
                    "\t\t\t\t\t\t</li>\n" +
                    "\t\t\t\t\t\t<li id=\"removeAllButton\">\n" +
                    "\t\t\t\t\t\t\t<a class=\"secondary-content\" onClick=\"removeAllProduct()\"><i class=\"material-icons\">fast_rewind</i></a>\n" +
                    "\t\t\t\t\t\t</li>\n" +
                    "\t\t\t\t\t</ul>\n" +
                    "\t\t\t\t</div>\n" +
                    "\n" +
                    "\t\t\t\t<div class=\"col s12 m5 center\">\n" +
                    "\t\t\t\t\t<div class=\"search_bar\">\n" +
                    "\t\t\t\t\t\t<form class=\"input-field card\">\n" +
                    "\t\t\t\t\t\t\t<input id=\"search_product_to_remove\" type=\"search\" placeholder=\"Rechercher...\" onKeyUp=\"searchProductToRemove()\">\n" +
                    "\t\t\t\t\t\t\t<label for=\"search\">\n" +
                    "\t\t\t\t\t\t\t\t<i class=\"material-icons\">search</i>\n" +
                    "\t\t\t\t\t\t\t</label>\n" +
                    "          \t\t\t\t</form>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\n" +
                    "\t\t\t\t\t<div>\n" +
                    "\t\t\t\t\t\t<ul id=\"list_products_added\" class=\"collection\">\n" +
                    "\t\t\t\t\t\t\t<li class=\"collection-item no_result hide\">\n" +
                    "\t\t\t\t\t\t\t\t<span class=\"product_name\">Aucun résultat</span>\n" +
                    "\t\t\t\t\t\t\t</li>\n" +
                    "\t\t\t\t\t\t</ul>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t\t<div class=\"col s12 offset-s1\">\n" +
                    "\t\t\t\t\tAffichage des graphiques possibles (Icônes)\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\n" +
                    "\t\t<div id=\"charts\" class=\"row\">\n" +
                    "\t\t\n" +
                    "\t\t<div class=\"divider\"></div>\n" +
                    "\t\t\t<div class=\"col s12\">\n" +
                    "\t\t\t\tAffichage des graphiques\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\t</body>\n" +
                    "\n" +
                    "\t<script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-2.1.1.min.js\"></script>\n" +
                    "  \t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js\"></script>\n" +
                    "\n" +
                    "\t<script type=\"text/javascript\">\n" +
                    "\t\t$(document).ready(function() {\n" +
                    "\t\t\t$('select').material_select();\n" +
                    "\t\t});\n" +
                    "\t</script>\n" +
                    "</html>");

            writer.close();





        }

        // ajout de htsa .
    }
}
