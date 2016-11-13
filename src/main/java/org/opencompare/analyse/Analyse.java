package org.opencompare.analyse;
import java.util.*;
/**
 * Created by Fonck on 07/11/2016.*
 * Defini les graphiques les plus adaptés à chaques caractéristiques (en fonction des produits )
 * donc doit dire si c'est un String il faut
 *
 */
public interface Analyse {
   String getFeatureType();
   Map getTableCompare();
}
