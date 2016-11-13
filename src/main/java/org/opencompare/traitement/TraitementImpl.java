package org.opencompare.traitement;

import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Fonck on 07/11/2016.
 */
public class TraitementImpl implements Traitement {
    Map<String, Map<String, String>> data;

    public TraitementImpl() {
        this.data = new Hashtable<String, Map<String, String>>();
    }

    public Map getData() {
        return this.data;
    }

    public void setData(File pcmFile) throws IOException {
        // On gère le format de fichier
        PCMLoader loader = new KMFJSONLoader();

        List<PCMContainer> pcmContainers = loader.load(pcmFile);

        for (PCMContainer pcmContainer : pcmContainers) {
            // On récupère la PCM
            PCM pcm = pcmContainer.getPcm();

            // On parcourts les cellules de la PCM
            for (Product product : pcm.getProducts()) {
                // On crée le hashtable contenu dans la liste
                Hashtable<String, String> values = new Hashtable<String, String>();

                for (Feature feature : pcm.getConcreteFeatures()) {
                    // Find the cell corresponding to the current feature and product
                    Cell cell = product.findCell(feature);

                    // Récupération de l'information contenu dans la cellule
                    String content = cell.getContent();

                    // On insére la clé et la valeur
                    values.put(feature.getName(), content);
                }

                // On insère la clé et la valeur (L'arrayList) dans le hashtable
                data.put(product.getKeyContent(), values);
            }
        }
    }

    @Override
    public ArrayList<String> getFeatureListe() {
        return new ArrayList<String>(this.data.get(getProductListe().get(0)).keySet());
    }


    @Override
    public ArrayList<String> getProductListe() {
        return new ArrayList<String>(this.data.keySet());
    }
}
