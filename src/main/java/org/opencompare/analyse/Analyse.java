package org.opencompare.analyse;

import java.util.*;

/**
 * Created by Fonck on 07/11/2016.*
 * Defini les graphiques les plus adaptés à chaques caractéristiques (en fonction des produits )
 * donc doit dire si c'est un String il faut
 */
public interface Analyse {
    /**
     * Retourne la PCM dans une Map
     *
     * @return Map de la PCM
     */
    Map getTableCompare();

    /**
     * Retourne une Map avec les occurances de la feature
     *
     * @param feature le nom de la feature dans la PCM
     * @return Retourne les occurances de la feature dans les data
     */
    Map getMapOccFeature(String feature);

    /**
     *
     * Teste si la feature est de type binaire
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type Binaire false sinon
     */
    boolean isBinaire(String feature);

    /**
     * Teste si la feature est de type Integer
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type Integer false sinon
     */
    boolean isInteger(String feature);

    /**
     * Teste si la feature est de type String
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type String false sinon
     */
    boolean isString(String feature);

    /**
     * Teste si la feature est de type date UK
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type DateUK false sinon
     */
    boolean isDateUK(String feature);

    /**
     *  Teste si la feature est de type date FR
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type DateFR false sinon
     */
    boolean isDateFR(String feature);

    /**
     * Teste si la feature est de type année
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type Year false sinon
     */
    boolean isYear(String feature);

    /**
     * Teste si la feature est de type pourcentage
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type Percent false sinon
     */
    boolean isPercent(String feature);

    /**
     * Teste si la feature est vraiment un pourcentage
     *
     * @param feature Nom de la feature à tester
     * @return True si la feature est de type PercentCent false sinon
     */
    boolean isPercentCent(String feature);

}
