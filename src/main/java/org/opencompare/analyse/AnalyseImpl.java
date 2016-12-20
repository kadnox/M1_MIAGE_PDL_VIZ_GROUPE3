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
            isNumber = value.matches("^(-|\\+)?[0-9]*(\\.|,)?[0-9]+$");
            if (!isNumber) {
                cptFalse++;

                isNumber = acceptable(cptFalse, laListe.size());

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
            isString = value.matches("[a-zA-Z0-9\\W{1,3}]*");
            if (!isString) {
                cptFalse++;
                isString = acceptable(cptFalse, laListe.size());
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
            isDate = value.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}") || value.matches("[0-9]{4}-[0-9]{2}") || value.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}") || value.matches("[0-9]{4}/[0-9]{2}") || correctYear(value)|| ((value.matches("\\w{2,9} [0-9]{2} [0-9]{4}")|| value.matches("\\w{2,9} [0-9]{2}")) || value.matches("\\w{2,9} [0-9]{4}"));
            if (!isDate) {
                cptFalse++;

                isDate = acceptable(cptFalse, laListe.size());

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
            isDate = ((((value.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}") || value.matches("[0-9]{2}-[0-9]{4}")) || value.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) || value.matches("[0-9]{2}/[0-9]{4}")) || correctYear(value)) || ((value.matches("[0-9]{2} \\w{2,9} [0-9]{4}")|| value.matches("[0-9]{2} \\w{2,9}")) || value.matches("\\w{2,9} [0-9]{4}")) ;
            if (!isDate) {
                cptFalse++;

                isDate = acceptable(cptFalse, laListe.size());

            }
        }
        return isDate;
    }

    private boolean correctYear(String year) {
        boolean correct = false;
        if (year.matches("[0-9]*")) {
            correct = (year.matches("[0-9]{4}") || year.matches("[0-9]{2}"));
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
            if (!isYear) {
                cptFalse++;

                isYear = acceptable(cptFalse, laListe.size());

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
            if (!isPercent) {
                cptFalse++;
                isPercent = acceptable(cptFalse, laListe.size());
            }
        }
        return isPercent;
    }

    @Override
    public boolean isPercentCent(String feature) {
        if (isPercent(feature)) {
            int cpt = 0;
            ArrayList<String> laListe = (ArrayList<String>) getTableCompare().get(new String(feature));
            Iterator<String> it = laListe.iterator();
            String value;
            while (it.hasNext()) {
                value = it.next();
                String[] split = value.split("%");
                if(split[0].matches("[0-9]{1,3}")) {
                    cpt = cpt + new Integer(split[0]);
                }
            }
            return cpt > 97 && cpt <= 100;
        } else {
            return false;
        }
    }

    private boolean acceptable(int cpt, int taille) {
        if (taille <= 10) {
            return (double) cpt / taille <= 0;
        } else if (taille <= 25) {
            return (double)cpt / taille <= 0.01;
        } else if (taille <= 50) {
            return (double)cpt / taille <= 0.04;
        } else if (taille <= 100) {
            return (double)cpt / taille <= 0.07;
        } else {
            return (double)cpt / taille <= 0.10;
        }

    }
}