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

public class ParameterEquals extends com.ichess.jvoodoo.ParameterHandle {

    private Object value;
    public ParameterEquals( Object value )
    {
        this.value = value;
    }

    @Override
    public void consume( Object actualValue )
    {
        Assert.assertEquals( "Wrong parameter value", actualValue, value );
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
