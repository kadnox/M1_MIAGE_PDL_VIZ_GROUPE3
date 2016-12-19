package org.opencompare.traitement;

import org.opencompare.analyse.Analyse;
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
    private Map<String, Map<String, String>> data;
    private Map<String, ArrayList<String>> dataInitial;
    private Analyse analyse;

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
        return new ArrayList<>(this.data.get(getProductListe().get(0)).keySet());
    }

    @Override
    public ArrayList<String> getProductListe() {
        return new ArrayList<>(this.data.keySet());
    }

    public boolean isBlankPCM() {
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
            return true;
        }

        return false;
    }

    public void removeUselessProduct() {
        List<String> productsToRemove = new ArrayList<>();

        Set<String> productKeys = data.keySet();
        Iterator<String> it1 = productKeys.iterator();

        while(it1.hasNext()) {
            String productKey = it1.next();
            Map<String, String> featuresMap = data.get(productKey);

            Set<String> featureKeys = featuresMap.keySet();
            Iterator<String> it2 = featureKeys.iterator();

            int nbFeatures = featureKeys.size();
            int blank = 0;
            while(it2.hasNext()) {
                String feature = it2.next();
                String value = featuresMap.get(feature);

                if(value.equals("")) {
                    blank++;
                }
            }

            for(String product : getProductListe()) {
                Double apparition;

                if (nbFeatures <= 2) {
                    apparition = 100.00;
                } else if (nbFeatures <= 4) {
                    apparition = 75.00;
                } else if (nbFeatures <= 6) {
                    apparition = 66.66;
                } else if (nbFeatures <= 8) {
                    apparition = 50.00;
                } else {
                    apparition = 40.00;
                }

                if (((blank / nbFeatures) * 100.00) >= apparition) {
                    if (!productsToRemove.contains(product)) {
                        productsToRemove.add(product);
                    }
                }
            }
        }

        for(String product : productsToRemove) {
            this.data.remove(product);
        }
    }

    public void removeUselessFeatures() {
        List<String> featuresToRemove = new ArrayList<>();
        Map<String, Double> occurrence = new Hashtable<>();

        Set<String> productKeys = data.keySet();
        Iterator<String> it1 = productKeys.iterator();

        double nbProduct = productKeys.size();
        while(it1.hasNext()) {
            String product = it1.next();
            Map<String, String> featuresMap = data.get(product);

            Set<String> featureKeys = featuresMap.keySet();
            Iterator<String> it2 = featureKeys.iterator();

            while(it2.hasNext()) {
                String feature = it2.next();
                String value = featuresMap.get(feature);

                if(value.equals("")) {
                    if(!occurrence.containsKey(feature)) {
                        occurrence.put(feature, 1.00);
                    } else {
                        Double valeurOccurence = occurrence.get(feature);
                        occurrence.put(feature, valeurOccurence + 1.00);
                    }
                }
            }

            for(String feature : getFeatureListe()) {
                if(occurrence.get(feature) != null) {
                    Double apparition;

                    if(nbProduct <= 10) {
                        apparition = 80.00;
                    } else if(nbProduct <= 25) {
                        apparition = 66.00;
                    } else if(nbProduct <= 50) {
                        apparition = 50.00;
                    } else if(nbProduct <= 100) {
                        apparition = 33.00;
                    } else {
                        apparition = 30.00;
                    }

                    if(((occurrence.get(feature)/nbProduct) * 100.00) >= apparition) {
                        if(!featuresToRemove.contains(feature)) {
                            featuresToRemove.add(feature);
                        }
                    }
                }
            }
        }

        it1 = productKeys.iterator();
        while(it1.hasNext()) {
            String product = it1.next();

            for(String feature : featuresToRemove) {
                this.data.get(product).remove(feature);
            }
        }
    }
}
