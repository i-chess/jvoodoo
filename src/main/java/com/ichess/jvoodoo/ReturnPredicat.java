//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

public class ReturnPredicat extends ReturnHandle {

    public static abstract class Predicat
    {
        public abstract Object returnValue(Object... parameters);
    }

    Predicat predicat;
    public ReturnPredicat(Predicat predicat)
    {
        this.predicat = predicat;
    }

    public Object consume( Object... parameters )
    {
        return predicat.returnValue(parameters);
    }

    @Override
    public String toString()
    {
        return "predicat " + predicat;
    }
}
