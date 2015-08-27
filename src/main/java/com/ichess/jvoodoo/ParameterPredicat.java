//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

public class ParameterPredicat extends ParameterHandle {

    public static abstract class Predicat
    {
        public abstract void validate(Object value);
    }

    private Object value;
    private Predicat predicat;
    public ParameterPredicat(Predicat predicat)
    {
        this.predicat = predicat;
    }

    @Override
    public void consume( Object actualValue )
    {
        value = actualValue;
        predicat.validate(value);
    }

    @Override
    public String toString() {
        return value == null ? "null" : value.toString();
    }
}
