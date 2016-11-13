package org.opencompare.analyse;

import org.opencompare.traitement.TraitementImpl;

import java.util.*;

/**
 * Created by Fonck on 07/11/2016.
 */
public class AnalyseImpl implements Analyse {
    private TraitementImpl traitement;
    private Map<String, ArrayList<String>> table = new HashMap<>();
    private Map temp = new HashMap<>();
    private ArrayList<String> liste = new ArrayList<>();
    //Set<String> featureTo...;

    public AnalyseImpl(TraitementImpl t) {
        this.traitement = t;
    }

    @Override
    public String getFeatureType() {
        return null;
    }

    public Map getTableCompare() {
        table.put("Product", traitement.getProductListe());
        for (String f : traitement.getFeatureListe()) {
            table.put(f,new ArrayList<>());
        }
        for(String p : traitement.getProductListe()) {

            for (String f : traitement.getFeatureListe()) {
                table.put(f,table.get(f).add());
            }

        }
        return table;
    }

}
