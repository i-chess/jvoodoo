//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================
package com.ichess.jvoodoo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUsingInterface {

    @Before
    public void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.BoxInterface");
    }

    @Test
    public void test_UsingInterface()
    {
        UsesBoxInterface tested = new UsesBoxInterface((BoxInterface)(UltimateObject.create("com.ichess.jvoodoo.BoxInterface", "fake box")));
        Scenario scenario = new Scenario();
        scenario.add(new Invocation("fake box", "getWidth", 100 ));
        assertEquals(100, tested.getBoxWidth());
        scenario.assertFinished();
    }
}
