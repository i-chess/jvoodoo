//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

/**
 * Created by me on 6/29/14.
 */
public class Construction extends Event {

    public Construction(String className, String name, ParameterHandle... parameters)
    {
        super(className, name, parameters);
    }

    @Override
    public String toString()
    {
        return "Construction of " + className + " instance '" + name + "' with parameters " + parameters;
    }
}
