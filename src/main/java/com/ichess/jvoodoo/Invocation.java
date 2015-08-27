//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

public class Invocation extends Event {

    private String methodName;
    private ReturnHandle returnHandle;
    public Invocation( String name, String methodName, ReturnHandle returnHandle, ParameterHandle... parameters)
    {
        super(null,name, parameters);
        this.methodName = methodName;
        this.returnHandle = returnHandle;
    }

    public Invocation( String name, String methodName, Object returnValue, ParameterHandle... parameters)
    {
        this(name, methodName, new ReturnValue(returnValue), parameters);
    }

    public Invocation( String name, String methodName, ParameterHandle... parameters)
    {
        this(name, methodName, new ReturnValue(Void.class), parameters);
    }

    public String getMethodName() {
        return methodName;
    }

    public ReturnHandle getReturnHandle() {
        return returnHandle;
    }

    @Override
    public String toString()
    {
        return "Invocation of " + name + "." + methodName + " with parameters " + parameters;
    }
}
