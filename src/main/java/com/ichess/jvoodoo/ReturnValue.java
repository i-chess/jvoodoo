//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

public class ReturnValue extends ReturnHandle {

    Object value;
    public ReturnValue(Object value)
    {
        this.value = value;
    }
    public Object consume( Object... parameters )
    {
        return value;
    }

    @Override
    public String toString()
    {
        return value == null ? "null" : value.toString();
    }
}
