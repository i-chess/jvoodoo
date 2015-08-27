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

public class Test_NotifierQueue extends TestCase {

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

    public void test_NormalVerifyEmptyNotification()
    {
        QueueThatNotifiesWhenEmpty tested = new QueueThatNotifiesWhenEmpty( (EmptyNotifier) UltimateObject.create("com.ichess.jvoodoo.EmptyNotifier", "fake notifier"));
        Scenario scenario = new Scenario();
        scenario.add(new Invocation("fake notifier", "notifyEmpty"));
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }

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
    /*
    public void test_WithParameters()
    {
        NotifierWithParameters notifier = (NotifierWithParameters) UltimateObject.create("com.ichess.jvoodoo.NotifierWithParameters", "fake notifier");
        QueueWrapperWithNotifierWithParameters tested = new QueueWrapperWithNotifierWithParameters( notifier );
        Scenario scenario = new Scenario();
        ParameterPredicat.Predicat predicat = new ParameterPredicat.Predicat()
        {
            public void validate(Object value)
            {
                assertTrue(((Integer)value).intValue() <= 1);
            }
        };
        scenario.add(new Invocation("fake notifier", "notifyQueue", new ParameterEquals(tested), new ParameterPredicat(predicat)));
        scenario.add(new Invocation("fake notifier", "notifyQueue", new ParameterEquals(tested), new ParameterPredicat(predicat)));
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }

    public void test_WithPSomethingAsUltimateObject()
    {
        NotifierWithSoemthing notifier = (NotifierWithSoemthing) UltimateObject.create("com.ichess.jvoodoo.NotifierWithSoemthing", "fake notifier");
        Something something = (Something) UltimateObject.create("com.ichess.jvoodoo.Something", "fake something");
        QueueWrapperThatPassesSomethingToNotifier tested = new QueueWrapperThatPassesSomethingToNotifier( notifier, something );
        Scenario scenario = new Scenario();
        scenario.add(new Invocation("fake notifier", "notifyEmpty", new ParameterIsUltimateObject("fake something")));
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }

    public void test_WithPSomethingAsUltimateObject2()
    {
        NotifierWithSoemthing notifier = (NotifierWithSoemthing) UltimateObject.create("com.ichess.jvoodoo.NotifierWithSoemthing", "fake notifier");
        Scenario scenario = new Scenario();
        scenario.add(new Construction("com.ichess.jvoodoo.Something", "fake something"));
        scenario.add(new Invocation("fake notifier", "notifyEmpty", new ParameterIsUltimateObject("fake something")));
        QueueWrapperThatPassesSomethingToNotifier tested = new QueueWrapperThatPassesSomethingToNotifier( notifier );
        tested.push("1");
        assertEquals("1", tested.pop());
        scenario.assertFinished();
    }
    */
}
