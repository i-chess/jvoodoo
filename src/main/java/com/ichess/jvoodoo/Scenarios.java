//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

import org.junit.Assert;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

public class Scenarios {

    private final static Logger LOGGER = Logger.getLogger(Scenarios.class.getName());
    private static ConcurrentMap<String, Scenario> scenarios = new ConcurrentHashMap<String, Scenario>();
    private static Scenario always = new Scenario("__VOODOO__ALWAYS__", false);

    public static void always(Event event)
    {
        always.add(event);
    }

    public static void add(Scenario scenario)
    {
        Scenario old = scenarios.get(scenario.getName());
        if (old != null)
        {
            old.assertFinished();
        }
        scenarios.put(scenario.getName(), scenario);
        LOGGER.fine("Added scenario '" + scenario.getName() + "'");
    }

    public static void assertAllScenariosFinished()
    {
        for (Scenario scenario : scenarios.values())
        {
            scenario.assertFinished();
        }
    }

    public static boolean allScenariosFinished()
    {
        for (Scenario scenario : scenarios.values())
        {
            if (! scenario.isFinished())
            {
                LOGGER.fine("Scenario '" + scenario.getName() + "' still got " + scenario.getEventsCount() + " events.");
                return false;
            }
        }
        return true;
    }

    public static void waitUntilAllScenariosFinished(int timeoutMS)
    {
        int t = 0;
        while ((! allScenariosFinished()) && (t<timeoutMS))
        {
            Utils.sleep(1000);
            t+=1000;
        }
        Assert.assertTrue("Not all scenarios finished", allScenariosFinished());
    }

    public static void expectConstruction(Object instance, String className, Object... parameters)
    {
        // String className = instance.getClass().getName();
        LOGGER.fine("Expecting construction of " + className + " with parameters " + Arrays.toString(parameters));
        for (Scenario scenario : scenarios.values())
        {
            LOGGER.fine("checking if scenario " + scenario.getName() + " expects construction of " + className);
            String instanceName = scenario.ifExpectedConstructionThenConsume(className, parameters);
            if (instanceName != null)
            {
                Voodoo.addInstance(instanceName, instance);
                return;
            }
        }

        String instanceName = always.ifExpectedConstructionThenConsume(className, parameters);
        if (instanceName != null)
        {
            Voodoo.addInstance(instanceName, instance);
            return;
        }

        LOGGER.warning("No scenario expected construction of " + className + " with parameters " +
            Arrays.toString(parameters));
        for (Scenario scenario : scenarios.values()) {
            if (scenario.expects() != null ) {
                LOGGER.info("Scenario " + scenario + " Expecting " + scenario.expects());
            }
        }
        Assert.fail("No scenario expected construction of " + className + " with parameters " +
            Arrays.toString(parameters));
    }

    private static void expectInvocationInternal(String name, String methodName, Object[] returnValue, Object... parameters)
    {
        LOGGER.fine("Expecting invocation of " + name + "." + methodName + " with parameters " + Arrays.toString(parameters));
        for (Scenario scenario : scenarios.values())
        {
            LOGGER.fine("checking if scenario " + scenario.getName() + " expects invocation of " + name + "." + methodName);
            if (scenario.ifExpectedInvocationThenConsume(name, methodName, returnValue, parameters))
            {
                LOGGER.fine("expected. return value is " + returnValue[0]);
                return;
            }
        }

        if (always.ifExpectedInvocationThenConsume(name, methodName, returnValue, parameters))
        {
            LOGGER.fine("expected. return value is " + returnValue[0]);
            return;
        }

        Scenario first;
        if (! scenarios.isEmpty())
        {
            first = scenarios.values().iterator().next();
            Event expected = first.expects();
            if (expected != null)
            {
                Assert.fail("Encountered invocation of " + name + "." + methodName +
                        " with parameters " + Arrays.toString(parameters) + " but expected " + expected);
                return;
            }
        }
        Assert.fail("No scenario expects invocation of " + name + "." + methodName + " with parameters " +
                      Arrays.toString(parameters));
    }

    public static Object expectInvocationReturns(String name, String methodName, Object... parameters)
    {
        Object[] returnValue = new Object[1];
        expectInvocationInternal(name, methodName, returnValue, parameters);
        return returnValue[0];
    }

    public static int expectInvocationReturns_int(String name, String methodName, Object... parameters)
    {
        Integer[] returnValue = new Integer[1];
        expectInvocationInternal(name, methodName, returnValue, parameters);
        return returnValue[0];
    }

    public static long expectInvocationReturns_long(String name, String methodName, Object... parameters)
    {
        Long[] returnValue = new Long[1];
        expectInvocationInternal(name, methodName, returnValue, parameters);
        return returnValue[0];
    }

    public static boolean expectInvocationReturns_boolean(String name, String methodName, Object... parameters)
    {
        Boolean[] returnValue = new Boolean[1];
        expectInvocationInternal(name, methodName, returnValue, parameters);
        return returnValue[0];
    }

    public static float expectInvocationReturns_float(String name, String methodName, Object... parameters)
    {
        Float[] returnValue = new Float[1];
        expectInvocationInternal(name, methodName, returnValue, parameters);
        return returnValue[0];
    }

    public static double expectInvocationReturns_double(String name, String methodName, Object... parameters)
    {
        Double[] returnValue = new Double[1];
        expectInvocationInternal(name, methodName, returnValue, parameters);
        return returnValue[0];
    }

    public static void expectInvocation(String name, String methodName, Object... parameters)
    {
        expectInvocationInternal(name, methodName, new Object[] { null }, parameters);
    }
}
