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

public class TestWithInterface  {

    @Before
    public void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.Something");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifier");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifierInterface");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.NotifierWithParameters");
    }

    @Test
    public void test_WithInterface()
    {
        EmptyNotifierInterface notifier = (EmptyNotifierInterface) UltimateObject.create("com.ichess.jvoodoo.EmptyNotifierInterface", "fake notifier");
        QueueThatNotifiesWhenEmptyUsingInterface tested = new QueueThatNotifiesWhenEmptyUsingInterface( notifier );
        Scenario scenario = new Scenario();
        scenario.add(new Invocation("fake notifier", "notifyEmpty"));
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }

}
