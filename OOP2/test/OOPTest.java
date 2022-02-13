/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import org.junit.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kutay
 */
public class OOPTest {
    
    public OOPTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() throws IOException {
    }
    
    @After
    public void tearDown() {
        Runtime r=Runtime.getRuntime();
        System.out.println("Collecting the garbage....");
        r.gc();
    }
    @Test
    public void testCalculateDistance() {
        assertEquals(0, DamerauLevenshtein.calculateDistance("", ""));
        assertEquals(0, DamerauLevenshtein.calculateDistance(" ", " "));
        assertEquals(1, DamerauLevenshtein.calculateDistance("", " "));
        assertEquals(1, DamerauLevenshtein.calculateDistance(" ", ""));
        assertEquals(0, DamerauLevenshtein.calculateDistance("test", "test"));
        assertEquals(1, DamerauLevenshtein.calculateDistance("Test", "test"));
        assertEquals(1, DamerauLevenshtein.calculateDistance("test", "testy"));
        assertEquals(1, DamerauLevenshtein.calculateDistance("testy", "test"));
        assertEquals(1, DamerauLevenshtein.calculateDistance("test", "tets"));
        assertEquals(1, DamerauLevenshtein.calculateDistance("test", "test "));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSourceNull() {
        DamerauLevenshtein.calculateDistance(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTargetNull() {
        DamerauLevenshtein.calculateDistance("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSourceAndTargetNull() {
        DamerauLevenshtein.calculateDistance(null, null);
    }
}
