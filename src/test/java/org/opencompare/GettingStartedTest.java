package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.CSVExporter;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
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
        File pcmFile = new File("pcms/Comparison_of_ATI_chipsets_1.pcm");

        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        // Load the file
        // A loader may return multiple PCM containers depending on the input format
        // A PCM container encapsulates a PCM and its associated metadata
        List<PCMContainer> pcmContainers = loader.load(pcmFile);

        //Structure de données pour le rangement des données - Possible modification si d'autres idées
        Map ht = new Hashtable<String, ArrayList<Hashtable<String, String>>>();

        for (PCMContainer pcmContainer : pcmContainers) {

            // Get the PCM
            PCM pcm = pcmContainer.getPcm();

            // Browse the cells of the PCM
            for (Product product : pcm.getProducts()) {
                //On initialise la liste de valeur
                List list = new ArrayList<Hashtable<String, String>>();

                for (Feature feature : pcm.getConcreteFeatures()) {

                    // Find the cell corresponding to the current feature and product
                    Cell cell = product.findCell(feature);

                    // Get information contained in the cell
                    String content = cell.getContent();
                    String rawContent = cell.getRawContent();
                    Value interpretation = cell.getInterpretation();

                    // Print the content of the cell
                    //System.out.println("(" + product.getKeyContent() + ", " + feature.getName() + ") = " + content);

                    //On crée le hashtable contenu dans la liste
                    Hashtable value = new Hashtable<String, String>();
                    //On insére la clé et la valeur
                    value.put(feature.getName(), content);

                    //On ajoute à la liste de valeur
                    list.add(value);
                }

                //On insère la clé et la valeur (L'arrayList) dans le hashtable
                ht.put(product.getKeyContent(), list);
            }

            //On récupère les clés
            Set<String> keys = ht.keySet();
            Iterator<String> it1 = keys.iterator();

            //Tant que l'on peut itérer
            while (it1.hasNext()) {
                //On récupère la clé
                String key = it1.next();
                //On récupère la valeur de cette clé
                ArrayList<Hashtable<String, String>> values = (ArrayList)ht.get(key);

                //On affiche
                System.out.println("Key : " + key);

                //Tant qu'il a des hashtable dans l'arrayList
                for(Hashtable<String, String> value : values) {
                    //On récupère les clés
                    Set<String> keysV = value.keySet();
                    Iterator<String> it2 = keysV.iterator();

                    //Tant que l'on peut itérer
                    while (it2.hasNext()) {
                        //On récupère la clé
                        String keyV = it2.next();
                        //On récupère la valeur de la clé
                        String valueV = value.get(keyV);

                        //On affiche
                        System.out.println("\t" + keyV + " : " + valueV);
                    }
                }
            }

            // Export the PCM container to CSV
            CSVExporter csvExporter = new CSVExporter();
            String csv = csvExporter.export(pcmContainer);

            // Write CSV content to file
            Path outputFile = Files.createTempFile("oc-", ".csv");
            Files.write(outputFile, csv.getBytes());
            System.out.println("PCM exported to " + outputFile);
        }

    }
}
