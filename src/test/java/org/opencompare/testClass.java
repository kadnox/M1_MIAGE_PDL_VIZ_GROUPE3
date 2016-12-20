package org.opencompare;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencompare.analyse.AnalyseImpl;
import org.opencompare.traitement.Traitement;
import org.opencompare.traitement.TraitementImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Emre on 20.12.2016.
 */

public class testClass {
    private static Traitement trait;

    @Before
    public void before() throws IOException {
        trait = new TraitementImpl();
        trait.setDataInitial(new File(System.getProperty("user.dir") + "/src/test/files/test.pcm"));
    }

    @Test
    public void testInitialisation() throws IOException {
        Map<String, ArrayList<String>> dataInitial = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();

        list.add("Mobile");
        list.add("PC");
        list.add("Console");

        List<String> listProduit = new ArrayList<>();
        listProduit.add("Samsun");
        listProduit.add("Haple");
        listProduit.add("Soni");

        list2.add("Oui");
        list2.add("Non");
        list2.add("Oui");

        list3.add("10.36");
        list3.add("90.33");
        list3.add("20");

        list4.add("20%");
        list4.add("50%");
        list4.add("30%");

        dataInitial.put("Coeur de metier", (ArrayList<String>) list);
        dataInitial.put("Products", (ArrayList<String>) listProduit);
        dataInitial.put("Bluetooh", (ArrayList<String>) list2);
        dataInitial.put("Prix", (ArrayList<String>) list3);
        dataInitial.put("PDM", (ArrayList<String>) list4);

        assertEquals(trait.getDataInitial(), dataInitial);
    }

    @Test
    public void testInitialisationData() throws IOException {
        trait = new TraitementImpl();
        trait.setData(new File(System.getProperty("user.dir") + "/src/test/files/test.pcm"));

        Map<String, Map<String, String>> data = new HashMap<>();

        Map<String, String> ssMap = new HashMap<>();
        ssMap.put("PDM", "20%");
        ssMap.put("Bluetooh", "Oui");
        ssMap.put("Prix", "10.36");
        ssMap.put("Coeur de metier", "Mobile");
        data.put("Samsun", ssMap);

        Map<String, String> ssMap2 = new HashMap<>();
        ssMap2.put("PDM", "30%");
        ssMap2.put("Bluetooh", "Oui");
        ssMap2.put("Prix", "20");
        ssMap2.put("Coeur de metier", "Console");
        data.put("Soni", ssMap2);

        Map<String, String> ssMap3 = new HashMap<>();
        ssMap3.put("PDM", "50%");
        ssMap3.put("Bluetooh", "Non");
        ssMap3.put("Prix", "90.33");
        ssMap3.put("Coeur de metier", "PC");
        data.put("Haple", ssMap3);

        assertEquals(trait.getData(), data);
    }

    @Test
    public void testAnalyseBinaire() throws IOException {
        AnalyseImpl anal = new AnalyseImpl(trait);

        assertTrue(anal.isBinaire("Bluetooh"));
    }

    @Test
    public void testAnalyseNumerique() throws IOException {
        AnalyseImpl anal = new AnalyseImpl(trait);

        assertFalse(anal.isInteger("Bluetooh"));
        assertTrue(anal.isInteger("Prix"));
    }

    @Test
    public void testAnalysePourcent() throws IOException {
        AnalyseImpl anal = new AnalyseImpl(trait);

        assertFalse(anal.isPercent("Bluetooh"));
        assertFalse(anal.isPercent("Prix"));
        assertTrue(anal.isPercent("PDM"));
        assertTrue(anal.isPercentCent("PDM"));
    }

    @Test
    public void testAnalyseDate() throws IOException {
        AnalyseImpl anal = new AnalyseImpl(trait);

        assertFalse(anal.isDateFR("Bluetooh"));
        assertFalse(anal.isDateUK("Prix"));
        assertFalse(anal.isDateUK("Bluetooh"));
        assertFalse(anal.isYear("Prix"));
    }


    @Test
    public void testAnalyseTexte() throws IOException {
        AnalyseImpl anal = new AnalyseImpl(trait);

        assertTrue(anal.isString("Prix"));
        assertTrue(anal.isString("Bluetooh"));
        assertTrue(anal.isString("PDM"));
    }

    @Test
    public void testAnalyseOccurence() throws IOException {
        AnalyseImpl anal = new AnalyseImpl(trait);
        Map<String, Integer> ssMap = new HashMap<>();

        ssMap.put("Oui", 2);
        ssMap.put("Non", 1);

        assertEquals(anal.getMapOccFeature("Bluetooh"), ssMap);
    }
}
