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

public class Test_ExtendUtilClass  {

    @Before
    public void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.ListOfIntegers");
    }

    @Test
    public void test_ExtendUtilClass() {
        Scenario scenario = new Scenario();
        scenario.add(new Construction("com.ichess.jvoodoo.ListOfIntegers", "fake list"));
        scenario.add(new Invocation("fake list", "add", true, new ParameterEquals(new Integer(1))));
        ListOfIntegers test = new ListOfIntegers();
        test.add(1);
        scenario.assertFinished();
    }
}
