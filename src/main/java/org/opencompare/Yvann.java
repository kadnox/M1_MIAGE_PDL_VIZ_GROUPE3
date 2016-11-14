package org.opencompare;

import org.opencompare.traitement.TraitementImpl;
import org.opencompare.analyse.AnalyseImpl;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by y-jos on 09/11/2016.
 */
public class Yvann {
    public static void main (String[] args){
        TraitementImpl trait = new TraitementImpl();
        try {
            trait.setData(new File("C:/Users/y-jos/Desktop/PCM/Comparison_of_audio_synthesis_environments_0.pcm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnalyseImpl anal = new AnalyseImpl(trait);
        System.out.println(anal.getTableCompare().toString());

    }
}
