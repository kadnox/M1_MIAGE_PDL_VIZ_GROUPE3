package org.opencompare.affichage;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fonck on 07/11/2016.
 */
public interface Affichage {
    /**
     * Genere les ressources ainsi que la visualisation nécessaire a l'utilisateur
     *
     * @param parentFolder Dossier contenant la PCM
     * @param fileName Nom du ficher PCM
     * @throws IOException
     */
    void HTMLGenerator(File parentFolder, String fileName) throws IOException;
}
