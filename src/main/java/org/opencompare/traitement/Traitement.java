package org.opencompare.traitement;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Fonck on 07/11/2016.
 */
public interface Traitement {
    public Map getData();
    public void setData(File pcmFile)  throws IOException;
}
