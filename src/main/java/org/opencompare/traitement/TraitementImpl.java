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
    Map<String, ArrayList<String>> dataInitial;

    public TraitementImpl() {
        this.data = new Hashtable<>();
        this.dataInitial = new HashMap<>();
    }

    public Map getData() {
        return this.data;
    }

    public Map getDataInitial() {
        return this.dataInitial;
    }

    public boolean setData(File pcmFile) {
        // On gère le format de fichier
        try {
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
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void setDataInitial(File pcmFile) throws IOException {
        // On gère le format de fichier
        PCMLoader loader = new KMFJSONLoader();

        List<PCMContainer> pcmContainers = loader.load(pcmFile);
        for (PCMContainer pcmContainer : pcmContainers) {
            // On récupère la PCM
            PCM pcm = pcmContainer.getPcm();
            for (Feature feature : pcm.getConcreteFeatures()) {
                this.dataInitial.put(feature.getName(), new ArrayList<>());
            }
            // On parcourt les cellules de la PCM
            for (Product product : pcm.getProducts()) {
                // On crée la liste contenu dans la liste
                for (Feature feature : pcm.getConcreteFeatures()) {
                    List<String> liste = (ArrayList) this.getDataInitial().get(feature.getName());
                    // Find the cell corresponding to the current feature and product
                    Cell cell = product.findCell(feature);

                    // Récupération de l'information contenu dans la cellule
                    String content = cell.getContent();
                    if (!content.equals(feature.getName())) {
                        liste.add(content);
                        this.dataInitial.put(feature.getName(), (ArrayList) liste);
                    }

                }
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

    public boolean isPcmExploitableBlank() {
        Set<String> productKeys = data.keySet();
        Iterator<String> it1 = productKeys.iterator();

        double nbBox = 0;
        double blank = 0;
        while(it1.hasNext()) {
            String product = it1.next();
            Map<String, String> featuresMap = data.get(product);

            Set<String> featureKeys = featuresMap.keySet();
            Iterator<String> it2 = featureKeys.iterator();

            while(it2.hasNext()) {
                String feature = it2.next();
                String value = featuresMap.get(feature);

                if(value.equals("")) {
                    blank++;
                }

                nbBox++;
            }
        }

        if(((blank/nbBox) * 100.00) >= 33.00) {
            return false;
        }

        return true;
    }
}
