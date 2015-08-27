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

public class ParameterIs extends ParameterHandle {

    private Class cls;
    public ParameterIs(Class cls)
    {
        this.cls = cls;
    }

    @Override
    public void consume( Object actualValue )
    {
        Assert.assertEquals("Object " + actualValue + " is not of class " + cls.getName(), actualValue.getClass(), cls);
    }
}
