//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================
package com.ichess.jvoodoo;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Test_ExtendUtilRandom  {

    @Before
    public void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.RandomWrapper");
    }

    @Test
    public void test_extendUtilRandom() {
        Scenario scenario = new Scenario();
        scenario.add(new Construction("com.ichess.jvoodoo.RandomWrapper", "fake random wrapper"));
        scenario.add(new Invocation("fake random wrapper", "nextInt", 10, new ParameterEquals(new Integer(100))));
        RandomWrapper test = new RandomWrapper();
        assertEquals(test.nextInt(100), 10);
        scenario.assertFinished();
    }
}
