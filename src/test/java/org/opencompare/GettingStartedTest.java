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
        File pcmFile = new File("C:/Users/y-jos/Desktop/PCM/Comparison_of_audio_synthesis_environments_0.pcm");

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

            FileWriter writer = new FileWriter("C:\\Users\\Testeur\\Desktop\\output.html");
            writer.write("<!doctype html>\n <html lang= fr >"+ newline);
            writer.write("<head>"+ newline);
            writer.write("<script src=\'http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js\'></script>" +newline);
            writer.write("<script type=\'text/javascript\' src=\'js/materialize.min.js\'></script>" +newline);
            writer.write("<link href=\'http://fonts.googleapis.com/icon?family=Material+Icons\' rel=\'stylesheet\'>" +newline);
            writer.write("<meta charset='utf-8'>" +newline);
            writer.write("<title> Projet ACO </title>" +newline);
            writer.write("</head>" +newline);
            writer.write("<body>" +newline);
            writer.write("<div class=\'row center\' style=\'background-color: red\'>" + newline);
            writer.write("<div class=\'col s3 input-field\' style=\'background-color: pink\'>" +newline);
            writer.write("<select multiple>" + newline);
            for(int i =0; i<liste_feature.length;i++){
               writer.write("<option value="+ i+"> "+ liste_feature[i] + "</option>" + newline);
            }
            writer.write("</select>"+newline );
            writer.write("</div>" +newline);
            writer.write("<div class=\'col s8 offset-s1\'>" +newline);
            writer.write("<div class='row' style='background-color: blue'>" +newline);
            writer.write("</div>" +newline);
            writer.write("</div>" +newline);
            writer.write("</div>" +newline);
            writer.write("</body>" +newline);
            writer.write("");
            //writer.write("<script src=\'http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js\'></script>" +newline);
            //writer.write("<script type=\'text/javascript\' src=\'js/materialize.min.js\'></script>" +newline);
            writer.close();

           /* System.out.println(ht.get(liste_produit[0].getClass()));
            System.out.println(ht.getClass());
            System.out.println(ht.get(liste_produit[0]));*/

           /* Set<String> test_htsa = ht.keySet();
            Iterator<String> it1_htsa = keys.iterator();

            it1_htsa.next();*/



        }

        // ajout de htsa .
    }
}
