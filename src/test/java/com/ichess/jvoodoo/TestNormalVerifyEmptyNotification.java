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

public class TestNormalVerifyEmptyNotification extends TestCase {

    @Override
    protected void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.Something");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifier");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifierInterface");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.NotifierWithParameters");
    }

    @Override
    protected void tearDown() throws Exception {

    }

    public void test_NormalVerifyEmptyNotification()
    {
        QueueThatNotifiesWhenEmpty tested = new QueueThatNotifiesWhenEmpty( (EmptyNotifier) UltimateObject.create("com.ichess.jvoodoo.EmptyNotifier", "fake notifier"));
        Scenario scenario = new Scenario();
        scenario.add(new Invocation("fake notifier", "notifyEmpty"));
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }

}
