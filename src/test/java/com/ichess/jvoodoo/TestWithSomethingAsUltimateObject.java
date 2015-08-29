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

public class TestWithSomethingAsUltimateObject  {

    @Before
    public void setUp() throws Exception {
        Voodoo.castVoodooOn("com.ichess.jvoodoo.Something");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifier");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.EmptyNotifierInterface");
        Voodoo.castVoodooOn("com.ichess.jvoodoo.NotifierWithParameters");
    }

    @Test
    public void test_WithSomethingAsUltimateObject()
    {
        NotifierWithSoemthing notifier = (NotifierWithSoemthing) UltimateObject.create("com.ichess.jvoodoo.NotifierWithSoemthing", "fake notifier with something");
        Something something = (Something) UltimateObject.create("com.ichess.jvoodoo.Something", "fake something");
        QueueThatPassesSomethingToNotifier tested = new QueueThatPassesSomethingToNotifier( notifier, something );
        Scenario scenario = new Scenario();
        scenario.add(new Invocation("fake notifier with something", "notifyEmpty", new ParameterIsUltimateObject("fake something")));
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }
}
