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

public class TestSettingUltimateObjectField {

    @Before
    public void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.Something");
    }

    @Test
    public void test_SettingUltimateObjectField()
    {
        Scenario scenario = new Scenario();
        Something something = (Something) UltimateObject.create("com.ichess.jvoodoo.Something", "fake something");
        something.someField = "some field";
        assertEquals("some field", something.someField);
        // TODO maybe fields access should be verified somehow
        scenario.assertFinished();
    }
}
