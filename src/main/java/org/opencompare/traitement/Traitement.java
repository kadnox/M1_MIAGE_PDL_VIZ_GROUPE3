package org.opencompare.traitement;

import org.opencompare.analyse.Analyse;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by Fonck on 07/11/2016.
 */
public interface Traitement {
    Map getData();

    Map getDataInitial();

    boolean setData(File pcmFile);

    void setDataInitial(File pcmFile) throws IOException;

    ArrayList<String> getFeatureListe();

    ArrayList<String> getProductListe();

    boolean isBlankPCM();

    void removeUselessProduct();

    void removeUselessFeatures();
}
