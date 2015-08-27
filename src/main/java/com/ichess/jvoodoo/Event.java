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
import java.util.List;

public abstract class Event {
    protected String className;
    protected String name;
    protected List<ParameterHandle> parameters;

    public Event(String className, String name, ParameterHandle... parameters)
    {
        this.className = className;
        this.name = name;
        this.parameters = Arrays.asList(parameters);
    }

    public String getClassName()
    {
        return className;
    }
    public String getName() { return name; }

    protected void consumeParameters(Object[] values)
    {
        Assert.assertTrue( "Expected " + parameters.size() + " parameters, but got " + values.length + " parameters.",
                parameters.size() == values.length);
        int index = 0;
        for (Object value : values)
        {
            parameters.get(index).consume(value);
            index ++;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        return o != null && name.equals(((Event)o).name) && className.equals(((Event)o).className);
    }


}
