package org.opencompare;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import static org.junit.Assert.*;

import org.opencompare.api.java.io.PCMLoader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by gbecan on 02/02/15.
 */
public class VisitorTest {

    @Test
    public void testMyPCMPrinter() throws IOException {

        // Load a PCM
        File pcmFile = new File("C:/Users/y-jos/Desktop/PCM/Comparison_of_audio_synthesis_environments_0.pcm");
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();
        assertNotNull(pcm);

        // Execute the printer
        MyPCMPrinter myPrinter = new MyPCMPrinter();
        myPrinter.print(pcm);
    }

}
