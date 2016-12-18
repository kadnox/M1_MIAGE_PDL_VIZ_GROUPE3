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
        //File pcmFile = new File("pcms/Comparison_of_ADC_software_5.pcm");
        File pcmFile = new File("pcms/Comparison_between_Esperanto_and_Ido_2.pcm");

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
               // System.out.println("la feature numero " + i + " est " + liste_feature[i]);
            }



            System.out.println("bla bla \n \n" +liste_feature[0] + "\n \n");
            String pb= (String) liste_feature[0];
            for( int i =0 ; i<pb.length(); i++){
                char pbsuite = pb.charAt(i) ;
                int asciipb = (int) pbsuite;
                System.out.println("le " + i +" eme caractere est " + pbsuite + " voila avec comme code ascii " + asciipb);
            }
            String newline = System.getProperty("line.separator");
            int asciiInsecable= 160;
            char insecable = (char) asciiInsecable;
            int asciiUnderscore = 95;
            char underscore = (char)asciiUnderscore;
            int asciiRetourLigne = 10;
            char charRetourLigne = (char) asciiRetourLigne;


            FileWriter writer = new FileWriter("templates/resultat.html");
            FileWriter writerjson = new FileWriter("templates/resultat.json");
            writerjson.write("{");




            writer.write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                    "\t\t<link href=\"http://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n" +
                    "  \t\t<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css\">\n" +
                    "\t\t<link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                    "\t\t\n" +
                    "\t\t<script type=\"text/javascript\" src=\"js/userConfig.js\"></script>\n" +
                    "\t\t<script type=\"text/javascript\" src=\"js/chart.js\"></script>\n" +
                  "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.min.js\"></script> " +
                    "\n" +
                    "\t\t<title>PCM Example </title>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "\t\t<div id=\"research\" class=\"row\">\n" +
                    "\t\t\t<div id=\"feature\" class=\"col s12 m12 l3 offset-l1 input-field\">\n" +
                    "\t\t\t\t<select id=\"select_feature\" onChange='enableChartIcons()'>\n" +
                    "\t\t\t\t\t<option value=\"0\" disabled selected>Select feature</option>");
            for(int i =1; i<liste_feature.length+1;i++){
                String tmp =(String) liste_feature[i-1];
              tmp =   tmp.toLowerCase().replace("'","_").replace('"','_').replace(insecable,underscore).replace(" ","_").replace("/","_par_").replace(charRetourLigne,underscore);
                writer.write("<option value="+i+">"+tmp+"</option>" + newline);
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
                writerjson.write("\""+liste_produit[i]+"\" : \n {");
                String ligne_html = "<span class='product_name present' ";

                for (int j =0 ; j<liste_feature.length; j++){
                    String nom_feature= (String)liste_feature[j];
                    String data_tmp = (String)temporaire.get(liste_feature[j]);
                    data_tmp = data_tmp.replace("'"," ").replace('"',' ').replace(charRetourLigne,underscore);
                    if(j<liste_feature.length-1){
                        writerjson.write("\""+ nom_feature +"\":\"" +data_tmp + "\", \n");
                    }
                    else{
                        writerjson.write("\""+ nom_feature +"\":" +data_tmp + "\n ");
                    }
                    nom_feature= nom_feature.toLowerCase().replace("'","_").replace('"','_').replace(insecable,underscore).replace(" ","_").replace("/","_par_").replace(charRetourLigne,underscore);



                    data_tmp = data_tmp.replace("'","_").replace('"','_').replace(insecable,underscore).replace(" ","_").replace(charRetourLigne,underscore);
                    ligne_html = ligne_html + "data-" + nom_feature + "='"+ data_tmp + "' ";
                }


                String  produit = liste_produit[i];
                produit = produit.replace("'","_").replace('"','_').replace(insecable,underscore).replace(" ","_").replace(charRetourLigne,underscore);


                writer.write(ligne_html +">"+ liste_produit[i] +"</span>"+ newline );
                writer.write("<a class=\"secondary-content\" data-name="+ produit +" onClick=\"addProduct(this)\">\n" +
                        "\t\t\t\t\t\t\t\t\t<i class=\"material-icons\">keyboard_arrow_right</i>\n" +
                        "\t\t\t\t\t\t\t\t</a>\n" +
                        "\t\t\t\t\t\t\t</li>");

               if(i <liste_produit.length-1){
                       writerjson.write("\n},");}
                else{
                   writerjson.write("\n}");
               }

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
                    "\n" +
                    "\t\t\t\t<div class=\"col s12 center chart_icons hide\">\n" +
                    "\t\t\t\t\t<a class=\"chart_icon\" onClick=\"generatePie()\">\n" +
                    "\t\t\t\t\t\t<img class=\"responsive-img\" src=\"../images/pie_chart.png\" alt=\"Pie chart\">\n" +
                    "\t\t\t\t\t</a>\n" +
                    "\n" +
                    "\t\t\t\t\t<a class=\"chart_icon\" onClick=\"generateBar()\">\n" +
                    "\t\t\t\t\t\t<img class=\"responsive-img\" src=\"../images/bar_chart.png\" alt=\"Bar chart\">\n" +
                    "\t\t\t\t\t</a>\n" +
                    "\n" +
                    "\t\t\t\t\t<a class=\"chart_icon\" onClick=\"generateRadar()\">\n" +
                    "\t\t\t\t\t\t<img class=\"responsive-img\" src=\"../images/radar_chart.png\" alt=\"Radar chart\">\n" +
                    "\t\t\t\t\t</a>\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\n" +
                    "\t\t<div class=\"row\">\n" +

                    "\n" +
                    "\t\t\t<div id=\"canvas_container\" class=\"col s12\">\n" +
                    "\t\t\t\t<canvas id=\"charts\"> </canvas>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +

                    "\n" +
                    "\t<script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-2.1.1.min.js\"></script>\n" +
                    "  \t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js\"></script>\n" +

                    "\n" +
                    "<script src=\"js/htsa.js\"> </script>"+
                    "\t<script type=\"text/javascript\">\n" +
                    "\t\t$(document).ready(function() {\n" +
                    "\t\t\t$('select').material_select();\n" +
                    "\t\t});\n" +
                    "\t</script>\n" +
                    "\t</body>\n" +
                    "</html>");

            writerjson.write("}");
            writer.close();
            writerjson.close();
            /*
            String soucis =(String) liste_feature[1];

            for(int i =0; i< soucis.length(); i ++){
             char charprobleme=   soucis.charAt(i);
              int  codeAscii = (int) charprobleme;
                System.out.println("la lettre " + charprobleme + " a pour code ascci " + codeAscii);
            }

            String espace = "_";
            char espacechar = espace.charAt(0);
            int codeEspace = (int) espacechar;
            System.out.println(" l'espace a pour code ascii" + codeEspace);


            */
        }

        // ajout de htsa . 160 insécable  32 l espace   95 _
    }
}
