package org.opencompare.analyse;

import org.opencompare.traitement.TraitementImpl;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Fonck on 07/11/2016.
 */
public class AnalyseImpl implements Analyse {
    private TraitementImpl traitement;
    private Map<String, ArrayList<String>> table = new HashMap<>();
    private ArrayList<String> liste = new ArrayList<>();

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
            Map<String,String> test = (Map) traitement.getData().get(p);
            String valueFeature ;
            for (String f : traitement.getFeatureListe()) {
                ArrayList<String> temp = new ArrayList<String>();
                temp = table.get(f);
                valueFeature =  (String) test.get(new String(f));
                temp.add(valueFeature);
                table.put(f,temp);
            }

        }
        return table;
    }





























}
