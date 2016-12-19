package org.opencompare;

import jdk.internal.cmm.SystemResourcePressureImpl;
import org.opencompare.traitement.TraitementImpl;
import org.opencompare.analyse.AnalyseImpl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by y-jos on 09/11/2016.
 */
public class Yvann {
    public static void main(String[] args) {
        TraitementImpl trait = new TraitementImpl();
        try {
            trait.setDataInitial(new File("pcms/Comparison_of_programming_languages_2.pcm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnalyseImpl anal = new AnalyseImpl(trait);
 //       System.out.println(trait.getDataInitial());
//        System.out.println(anal.getMapOccFeature("Name"));
//        System.out.println(anal.isBinaire("Name"));
//        System.out.println(anal.isDateFR("Name"));
        String value;
        value = "23/Septembre/2016";
        System.out.println("\n"+ anal.isPercentCent(""));
//        System.out.println("\n"+trait.getDataInitial());
//        Calendar cal = Calendar.getInstance();
//        cal.getWeekYear();
       // System.out.println(cal.getWeekYear());
    }
}
