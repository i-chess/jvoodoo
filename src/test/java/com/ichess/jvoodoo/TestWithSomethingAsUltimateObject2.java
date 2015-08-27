//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================
package com.ichess.jvoodoo;
import com.ichess.jvoodoo.*;
import junit.framework.TestCase;

public class TestWithSomethingAsUltimateObject2 extends TestCase {

    @Override
    protected void setUp() throws java.lang.Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.Something");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifier");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifierInterface");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.NotifierWithParameters");
    }

    @Override
    protected void tearDown() throws java.lang.Exception {

    }

    public void test_WithSomethingAsUltimateObject2()
    {
        NotifierWithSoemthing notifier = (NotifierWithSoemthing) UltimateObject.create("com.ichess.jvoodoo.NotifierWithSoemthing", "fake notifier with something 2");
        Scenario scenario = new Scenario();
        scenario.add(new Construction("com.ichess.jvoodoo.Something", "fake something 2"));
        scenario.add(new Invocation("fake notifier with something 2", "notifyEmpty", new ParameterIsUltimateObject("fake something 2")));
        QueueThatPassesSomethingToNotifier tested = new QueueThatPassesSomethingToNotifier( notifier );
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }

}
