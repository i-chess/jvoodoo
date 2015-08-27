//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

public class ParameterSave extends ParameterHandle {

    private Object value = null;
    public ParameterSave()
    {
    }

    public Object getValue() {
        return value;
    }

    @Override
    public void consume( Object actualValue )
    {
        value = actualValue;
    }

    @Override
    public String toString() {
        return value == null ? "null" : value.toString();
    }
}
