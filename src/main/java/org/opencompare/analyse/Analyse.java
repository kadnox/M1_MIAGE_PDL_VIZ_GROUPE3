package org.opencompare.analyse;

import java.util.*;

/**
 * Created by Fonck on 07/11/2016.*
 * Defini les graphiques les plus adaptés à chaques caractéristiques (en fonction des produits )
 * donc doit dire si c'est un String il faut
 */
public interface Analyse {

    Map getTableCompare();

    Map getMapOccFeature(String feature);

    boolean isBinaire(String feature);

    boolean isInteger(String feature);

    boolean isString(String feature);

    boolean isDateUK(String feature);

    boolean isDateFR(String feature);

    boolean isYear(String feature);

    boolean isPercent(String feature);

    boolean isPercentCent(String feature);

}
