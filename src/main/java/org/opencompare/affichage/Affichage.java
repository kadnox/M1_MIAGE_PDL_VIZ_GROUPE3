package org.opencompare.affichage;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fonck on 07/11/2016.
 */
public interface Affichage {
    void HTMLGenerator(File parentFolder, String fileName) throws IOException;
}
