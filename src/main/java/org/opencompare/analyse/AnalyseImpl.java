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
    public Map getTableCompare() {
        return traitement.getDataInitial();
    }

    @Override
    public Map getMapOccFeature(String feature) {
        Map<String, Integer> mapOccFeature = new HashMap<>();
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        String value;
        Iterator<String> it = laListe.iterator();
        while (it.hasNext()) {
            value = it.next();
            if (mapOccFeature.containsKey(new String(value))) {
                mapOccFeature.put(value, mapOccFeature.get(value) + 1);
            } else {
                mapOccFeature.put(value, 1);
            }

        }
        return mapOccFeature;
    }

    @Override
    public boolean isBinaire(String feature) {
        Map<String, Integer> mapOccFeature = getMapOccFeature(feature);
        Collection col = mapOccFeature.values();
        return col.size() <= 3;
    }

    @Override
    public boolean isInteger(String feature) {
        int cptFalse = 0;
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isNumber = true;
        while (it.hasNext() && isNumber) {
            value = it.next();
            isNumber = value.matches("[0-9]*");
            if(!isNumber){
                cptFalse++;
                if(cptFalse/laListe.size() <= 0.33) {
                    isNumber = true;
                }
            }
        }
        return isNumber;
    }

    @Override
    public boolean isString(String feature) {
        int cptFalse = 0;
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isString = true;
        while (it.hasNext() && isString) {
            value = it.next();
            isString = value.matches("[a-zA-Z0-9]*") || value.matches("\\W{1,3}");
            if(!isString){
                cptFalse++;
                if(cptFalse/laListe.size() <= 0.33) {
                    isString = true;
                }
            }
        }

        return isString;
    }

    @Override
    public boolean isDateUK(String feature) {
        int cptFalse = 0;
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isDate = true;
        while (it.hasNext() && isDate) {
            value = it.next();
            isDate = value.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}") || value.matches("[0-9]{4}-[0-9]{2}") || value.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}") || value.matches("[0-9]{4}/[0-9]{2}") || correctYear(value);
            if(!isDate){
                cptFalse++;
                if(cptFalse/laListe.size() <= 0.33) {
                    isDate = true;
                }
            }
        }
        return isDate;
    }

    @Override
    public boolean isDateFR(String feature) {
        int cptFalse = 0;
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isDate = true;
        while (it.hasNext() && isDate) {
            value = it.next();
            isDate = value.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}") || value.matches("[0-9]{2}-[0-9]{4}") || value.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || value.matches("[0-9]{2}/[0-9]{4}") || correctYear(value);
            if(!isDate){
                cptFalse++;
                if(cptFalse/laListe.size() <= 0.33) {
                    isDate = true;
                }
            }
        }
        return isDate;
    }

    private boolean correctYear(String year) {
        Calendar cal = Calendar.getInstance();
        int today = cal.getWeekYear();
        boolean correct = false;
        if(year.matches("[0-9]*")){
            int y = new Integer(year);
           correct = (year.matches("[0-9]{4}") || year.matches("[0-9]{2}")) && (y < today);
        }
        return correct;
    }

    @Override
    public boolean isYear(String feature) {
        int cptFalse = 0;
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isYear = true;
        while (it.hasNext() && isYear) {
            value = it.next();
            isYear = correctYear(value);
            if(!isYear){
                cptFalse++;
                if(cptFalse/laListe.size() <= 0.33) {
                    isYear = true;
                }
            }
        }
        return isYear;
    }

    @Override
    public boolean isPercent(String feature) {
        int cptFalse = 0;
        ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
        Iterator<String> it = laListe.iterator();
        String value;
        boolean isPercent = true;
        while (it.hasNext() && isPercent) {
            value = it.next();
            isPercent = value.matches("[0-9]{1,3}%");
            if(!isPercent){
                cptFalse++;
                if(cptFalse/laListe.size() <= 0.33) {
                    isPercent = true;
                }
            }
        }
        return isPercent;
    }

    @Override
    public boolean isPercentCent(String feature) {
        return false;
    }
}