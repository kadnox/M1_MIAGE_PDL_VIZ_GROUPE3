package org.opencompare.analyse;

import org.opencompare.traitement.Traitement;

/**
 * Created by Fonck on 07/11/2016.
 */
public class AnalyseImpl implements Analyse {
    private Traitement traitement;
    //Set<String> featureTo...;

    public AnalyseImpl(Traitement t) {
        this.traitement = t;
    }

    public String getFeatureType() {
        return new String("");
    }
}
