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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class Scenario {

    private final static Logger LOGGER = Logger.getLogger(Scenario.class.getName());
    private BlockingQueue<Event> events = new LinkedBlockingQueue<Event>();
    private String name;
    private boolean expectInOrder = true;

    public Scenario(String name, boolean expectInOrder)
    {
        LOGGER.fine("Scenario '" + name + "' created");
        this.name = name;
        this.expectInOrder = expectInOrder;
        Scenarios.add(this);
    }

    public Scenario(String name)
    {
        this(name, true);
    }
    public Scenario()
    {
        this("DEFAULT", true);
    }

    public String getName()
    {
        return name;
    }

    public void add( Event event )
    {
        LOGGER.fine("Scenario " + name + " added " + event);
        events.offer(event);
    }

    public Event expects()
    {
        return events.peek();
    }

    private String ifExpectedConstructionThenConsume(Event event, String className, Object... parameters)
    {
        if (event != null && event instanceof Construction && (event).getClassName().equals(className))
        {
            LOGGER.fine("Scenario " + name + " expected construction of " + className + " to instance name " +
                    event.getName() + " with parameters " + Arrays.toString(parameters));
            event.consumeParameters(parameters);
            String instanceName = event.getName();
            return instanceName;
        }
        return null;
    }

    public String ifExpectedConstructionThenConsume(String className, Object... parameters)
    {
        if (expectInOrder) {
            Event event = events.peek();
            String instanceName = ifExpectedConstructionThenConsume(event, className, parameters);
            if (instanceName != null)
            {
                events.poll();
                return instanceName;
            }
        }
        else {
            for (Event event : events) {
                String returnValue = ifExpectedConstructionThenConsume(event, className, parameters);
                if (returnValue != null) {
                    return returnValue;
                }
            }
        }
        return null;
    }

    public boolean ifExpectedInvocationThenConsume(Event event, String instanceOrClassName, String methodName, Object[] returnValue, Object... parameters)
    {
        if (event == null)
        {
            LOGGER.fine("Scenario " + name + " does not expect " + instanceOrClassName + "." + methodName + " : no event");
            return false;
        }
        if (! (event instanceof Invocation))
        {
            LOGGER.fine("Scenario " + name + " does not expect " + instanceOrClassName + "." + methodName + " : expecting " + event + " which is not invocation");
            return false;
        }
        if (! ((event).getName().equals(instanceOrClassName)))
        {
            LOGGER.fine("Scenario " + name + " does not expect instance or class name '" + instanceOrClassName + "' : expecting event name '" + event.getName() + "'");
            return false;
        }
        if (! (((Invocation)(event)).getMethodName().equals(methodName)))
        {
            LOGGER.fine("Scenario " + name + " does not expect '" + methodName + "' : expecting method name '" + ((Invocation) event).getMethodName() + "'");
            return false;
        }
        //LOGGER.fine("Scenario " + name + " expected invocation of " + name + "." + methodName + " with parameters " + Arrays.toString(parameters) + " return value " + ((Invocation) event).getReturnHandle());
        event.consumeParameters(parameters);
        Object returned = null;
        try
        {
            returned = ((Invocation)event).getReturnHandle().consume(parameters);
        }
        catch (ArrayStoreException ex)
        {
            Assert.fail("Illegal or not expected value " + returned);
        }
        returnValue[0] = returned;
        return true;
    }

    public boolean ifExpectedInvocationThenConsume(String instanceOrClassName, String methodName, Object[] returnValue, Object... parameters)
    {
        if (expectInOrder) {
            Event event = events.peek();
            if (ifExpectedInvocationThenConsume(event, instanceOrClassName, methodName, returnValue, parameters))
            {
                events.poll();
                return true;
            }
        }
        else {
            for (Event event : events) {
                if (ifExpectedInvocationThenConsume(event, instanceOrClassName, methodName, returnValue, parameters))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void waitUntilFinished(int timeoutMS)
    {
        int t = 0;
        while ((! events.isEmpty()) && (t<timeoutMS))
        {
            Utils.sleep(100);
            t+=100;
        }
        Assert.assertTrue("Scenario " + name + " was not finished in " + timeoutMS + " ms", events.isEmpty());
    }

    public int getEventsCount()
    {
        return events.size();
    }

    public boolean isFinished()
    {
        return events.isEmpty();
    }

    public void assertFinished()
    {
        Assert.assertTrue("Scenario " + name + " not finished", events.isEmpty());
    }


}
