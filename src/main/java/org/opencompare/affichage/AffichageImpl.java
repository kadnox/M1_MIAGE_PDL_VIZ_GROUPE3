package org.opencompare.affichage;

import org.opencompare.analyse.Analyse;

import java.io.File;

/**
 * Created by Fonck on 07/11/2016.
 */
public class AffichageImpl implements Affichage {
    Analyse analyse;
    File file;

    public AffichageImpl(Analyse a) {
        this.analyse = a;
        this.file = new File("NOM_PCM.html");
    }

    public File HTMLGenerator() {
        return this.file;
    }
}
