package org.opencompare.analyse;

import org.opencompare.traitement.Traitement;
import org.opencompare.traitement.TraitementImpl;

import java.util.*;

/**
 * Created by Fonck on 07/11/2016.
 */
public class AnalyseImpl implements Analyse {
    private Traitement traitement;
    private ArrayList<String> liste = new ArrayList<>();

    public AnalyseImpl(Traitement t) {
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

    public boolean isString(String feature){
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        System.currentTimeMillis();
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isString = true;
        while (it.hasNext() && isString){
            value = it.next();
            isString = value.matches("[0-9]{4}");
        }
        return isString;
    }

    public boolean isDateUK(String feature){
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isDate = true;
        while (it.hasNext() && isDate){
            value = it.next();
            isDate = value.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}") || value.matches("[0-9]{4}-[0-9]{2}");
        }
        return isDate;
    }

    public boolean correctYear(String year){
        Calendar cal = Calendar.getInstance();
        cal.getWeekYear();
        return year.matches("[0-9]{4} || [0-9]{2}");
    }

    public boolean isPercent(String feature){
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isPercent = true;
        while (it.hasNext() && isPercent){
            value = it.next();
            isPercent = value.matches("[0-9]{1,3}%");
        }
        return isPercent;
    }
}