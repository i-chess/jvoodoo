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

public class Test_InheritedMethods  {

    @Before
    public void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.SomethingSubClass");
    }

    @Test
    public void test_inheritedPublicMethod() {
        Scenario scenario = new Scenario();
        scenario.add(new Construction("com.ichess.jvoodoo.Something", "fake something"));
        scenario.add(new Construction("com.ichess.jvoodoo.SomethingSubClass", "fake something sub class"));
        scenario.add(new Invocation("fake something sub class", "square", 100, new ParameterEquals(10)));
        SomethingSubClass test = new SomethingSubClass();
        test.square(10);
        scenario.assertFinished();
    }
}
