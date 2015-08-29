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

public class Test_SubClassConstructorAccessTheClass extends TestCase {

    @Override
    protected void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.SomethingSubClass");
    }

    @Override
    protected void tearDown() throws Exception {

    }

    public void test_SubClassConstructorAccessTheClass() {
        Scenario scenario = new Scenario();
        scenario.add(new Construction("com.ichess.jvoodoo.Something", "fake something"));
        scenario.add(new Construction("com.ichess.jvoodoo.SomethingSubClass", "fake something sub class"));
        SomethingSubClass test = new SomethingSubClass();
        scenario.assertFinished();
    }

}
