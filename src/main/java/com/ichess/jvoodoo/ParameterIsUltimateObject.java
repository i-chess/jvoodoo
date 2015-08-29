//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

import static org.junit.Assert.assertEquals;

public class ParameterIsUltimateObject extends com.ichess.jvoodoo.ParameterHandle {

    private String name;
    public ParameterIsUltimateObject(String name)
    {
        this.name = name;
    }

    @Override
    public void consume(Object actualValue)
    {
        assertEquals( com.ichess.jvoodoo.Voodoo.getVoodooInstance(name), actualValue );
    }

    @Override
    public String toString() {
        return "ultimate object : '" + name + "'";
    }

}
