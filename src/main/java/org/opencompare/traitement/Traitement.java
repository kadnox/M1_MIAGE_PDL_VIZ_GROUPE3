package org.opencompare.traitement;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by Fonck on 07/11/2016.
 */
public interface Traitement {
    Map getData();
    void setData(File pcmFile)  throws IOException;
    void setDataInitial(File pcmFile) throws IOException;
    ArrayList<String> getFeatureListe();
    ArrayList<String> getProductListe();
}
