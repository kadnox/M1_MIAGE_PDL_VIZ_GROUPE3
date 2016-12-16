package org.opencompare.analyse;

import org.opencompare.traitement.TraitementImpl;

import java.util.*;

/**
 * Created by Fonck on 07/11/2016.
 */
public class AnalyseImpl implements Analyse {
    private TraitementImpl traitement;
    private ArrayList<String> liste = new ArrayList<>();

    public AnalyseImpl(TraitementImpl t) {
        this.traitement = t;
    }

    @Override
    public String getFeatureType(String feature) {
        return null;
    }

    public Map getTableCompare() {
        return traitement.getDataInitial();
    }

    @Override
    public Map getMapOccFeature(String feature) {
        Map<String,Integer> mapOccFeature = new HashMap<>();
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        String value;
        int occ;
        Iterator<String> it = laListe.iterator();
        while(it.hasNext()){
            value = it.next();
            if(mapOccFeature.containsKey(new String(value))) {
                mapOccFeature.put(value, mapOccFeature.get(value) + 1);
            }else{
                mapOccFeature.put(value,0);
            }

        }
        //System.out.println(mapOccFeature);
        return mapOccFeature;
    }

    public boolean isBinaire(String feature){
        Map<String,Integer> mapOccFeature = getMapOccFeature(feature);
        System.out.println(mapOccFeature);
        Collection col = mapOccFeature.values();
        return col.size() <= 3;
    }

    public boolean isInteger(String feature){
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isNumber = true;
        while (it.hasNext() && isNumber){
            value = it.next();
            isNumber = value.matches("[0-9]*");
        }
        return isNumber;
    }
}