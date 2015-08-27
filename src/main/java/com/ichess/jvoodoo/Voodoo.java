//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

import javassist.*;
import org.junit.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Voodoo {

    private final static Logger LOGGER = Logger.getLogger(Scenario.class.getName());
    private static ClassPool _classPool;
    static
    {
        try
        {
            _classPool = ClassPool.getDefault();
        }
        catch (Exception ex)
        {
            LOGGER.warning("Could not load jvoodoo");
            Assert.fail("Could not load jvoodoo");
        }
    }

    private static Map<String, Class> _voodooClasses = new HashMap<String, Class>();
    private static Map<String, Object> _voodooInstances = new HashMap<String, Object>();
    private static final String __VOODOO__MOCK__SUFFIX__ = "__VOODOO__MOCK__SUFFIX__";

    public static boolean isVoodooClass(String className)
    {
        return _voodooClasses.containsKey(className);
    }

    public static boolean isVoodooInstance(String name)
    {
        return _voodooInstances.containsKey(name);
    }

    public static Object getVoodooInstance(String name)
    {
        Object object =_voodooInstances.get(name);
        Assert.assertNotNull("Could not find jvoodoo instance '" + name + "'", object);
        return object;
    }

    public static String VOODOO_CONSTRUCTING_CLASS;

    public static void addInstance(String instanceName, Object instance)
    {
        _voodooInstances.put(instanceName, instance);
    }

    public static String getInstanceName(Object instance)
    {
        for (String name : _voodooInstances.keySet())
        {
            if ( _voodooInstances.get(name) == instance)
            {
                LOGGER.fine("instance " + instance + " name is '" + name + "'");
                return name;
            }
        }
        Assert.fail("Could not find instance name for jvoodoo object " + instance);
        return null;
    }

    public static Object instance(String className, String instanceName)
    {
        if ( ! isVoodooClass(className) ) {
            castVoodooOn(className);
        }
        Assert.assertTrue( "Instance name '" + instanceName + "' is actually a class name", ! _voodooClasses.containsKey(instanceName));
        Assert.assertTrue( "Instance name '" + instanceName + "' already exists", ! _voodooInstances.containsKey(instanceName));
        try
        {
            Class c = _voodooClasses.get(className);
            if (c.isInterface())
            {
                className = className + __VOODOO__MOCK__SUFFIX__;
                c = _voodooClasses.get(className);
            }
            Assert.assertNotNull("Could not find jvoodoo class '" + className + "'", c);
            VOODOO_CONSTRUCTING_CLASS = className;
            for (Method method : c.getDeclaredMethods())
            {
                LOGGER.fine(c.getName() + " has method " + method.getName() + " modifiers " + Integer.toHexString(method.getModifiers())
                        + " args " + Arrays.toString(method.getParameterTypes()));
            }
            for (Constructor constructor : c.getDeclaredConstructors())
            {
                LOGGER.fine(c.getName() + " has constructor " + constructor.getName() + " modifiers " + Integer.toHexString(constructor.getModifiers()) +
                        " args " + Arrays.toString(constructor.getParameterTypes()));
            }
            LOGGER.fine(" class " + c.getName() + " super class " + c.getSuperclass().getName() +
                    " interfaces " + Arrays.toString(c.getInterfaces()) + " modifiers " + Integer.toHexString(c.getModifiers()));
            Object o = c.newInstance();
            VOODOO_CONSTRUCTING_CLASS = null;
            addInstance(instanceName, o);
            LOGGER.fine("Created new instance of " + c.getName() + " named '" + instanceName + "'");
            return o;
        }
        catch (Throwable ex)
        {
            LOGGER.warning("Could not create instance of '" + className + "'");
            Assert.fail("Could not create instance of '" + className + "'");
        }
        return null;
    }

    private static void setPublic(CtMember ct)
    {
        int modifiers = ct.getModifiers();
        modifiers &= ~ (java.lang.reflect.Modifier.PRIVATE);
        modifiers &= ~ (java.lang.reflect.Modifier.PROTECTED);
        modifiers |= (java.lang.reflect.Modifier.PUBLIC);
        ct.setModifiers(modifiers);
    }

    public static void castVoodooOn( String className )
    {
        if (isVoodooClass(className))
        {
            LOGGER.fine("already casted voodoo on " + className);
            return;
        }
        StringBuffer body;
        try
        {
            LOGGER.fine("Casting jvoodoo on class " + className);
            CtClass ctClass = _classPool.get(className);

            Assert.assertFalse("Class '" + className + "' is frozen", ctClass.isFrozen() );
            CtClass baseClass = ctClass;
            CtClass implementingClass = null;

            if ( ctClass.isInterface() )
            {
                String implementingClassName = className + __VOODOO__MOCK__SUFFIX__;
                implementingClass = _classPool.makeClass(implementingClassName);
                implementingClass.setInterfaces(new CtClass[]{ctClass});
                ctClass = implementingClass;
                className = implementingClassName;
            }

            CtField[] ctFields = ctClass.getDeclaredFields();
            for (CtField ctField : ctFields)
            {
                LOGGER.fine("found field " + ctField.getName() + " type " + ctField.getType().getName() + " modifiers " + Integer.toHexString(ctField.getModifiers()));
                setPublic(ctField);
            }

            CtConstructor[] ctConstructors = ctClass.getDeclaredConstructors();
            boolean hasDefaultConstructor = false;
            for (CtConstructor ctConstructor : ctConstructors)
            {
                LOGGER.fine("found constructor " + ctConstructor.getName() + " signature " + ctConstructor.getSignature());
                body = new StringBuffer();
                String debugLine = "com.ichess.jvoodoo.Utils.LOGGER.fine( \">>> " + className + ":" + ctConstructor.getSignature() + "\"); ";
                body.append("{" +
                    debugLine +
                    "if ( ! \"" + className + "\".equals( com.ichess.jvoodoo.Voodoo.VOODOO_CONSTRUCTING_CLASS ) ) " + " com.ichess.jvoodoo.Scenarios.expectConstruction( this, $args ); }");
                LOGGER.fine("new constructor body : '" + body + "'");
                ctConstructor.setBody(body.toString());
                setPublic(ctConstructor);
                if ( ctConstructor.getParameterTypes().length == 0)
                {
                    hasDefaultConstructor = true;
                }
            }
            if (! hasDefaultConstructor)
            {
                CtConstructor defaultConstructor = new CtConstructor( new CtClass[0], ctClass);
                body = new StringBuffer();
                body.append("{ if ( ! \"" + className + "\".equals( com.ichess.jvoodoo.Voodoo.VOODOO_CONSTRUCTING_CLASS ) ) " + " com.ichess.jvoodoo.Scenarios.expectConstruction( this, $args ); }");
                defaultConstructor.setBody(body.toString());
                ctClass.addConstructor(defaultConstructor);
                setPublic(defaultConstructor);
                LOGGER.fine("new default constructor for " + ctClass.getName() + " modifiers " + Integer.toHexString(defaultConstructor.getModifiers()) + " body : '" + body + "'");
            }

            CtMethod[] ctMethods = baseClass.getDeclaredMethods();
            for (CtMethod ctMethod : ctMethods)
            {
                LOGGER.fine("found method " + ctClass.getName() + ":" + ctMethod.getName() + " declared by " + ctMethod.getDeclaringClass().getName() +
                        " modifiers " + Integer.toHexString(ctMethod.getModifiers()) + " signature " + ctMethod.getSignature());

                CtMethod actualMethod = ctMethod;
                if (implementingClass != null)
                {
                    ctMethod.setModifiers(ctMethod.getModifiers() & (~java.lang.reflect.Modifier.FINAL));

                    actualMethod = new CtMethod(ctMethod.getReturnType(), ctMethod.getName(), ctMethod.getParameterTypes(), implementingClass);
                    implementingClass.addMethod(actualMethod);
                }

                String invocationMethod = "com.ichess.jvoodoo.Scenarios.expectInvocation";
                String name;
                String debugLine = "com.ichess.jvoodoo.Utils.LOGGER.fine( \">>> " + className + ":" + ctMethod.getName() + "\"); ";
                body = new StringBuffer();
                boolean isStatic = ((actualMethod.getModifiers() & Modifier.STATIC) != 0);
                boolean returnSomething = (actualMethod.getReturnType() != CtClass.voidType);
                CtClass returnClass = actualMethod.getReturnType();
                String returnClassName = returnClass.getName();
                String returnStatement = "";
                String castResult = "";
                if (returnClass != CtClass.voidType) {
                    returnStatement = "return ";
                    invocationMethod = invocationMethod + "Returns";
                    castResult = "(" + returnClassName + ")";
                    if (returnClass.isPrimitive()) {
                        invocationMethod += "_" + returnClass.getName();
                    }
                }
                if (isStatic)
                {
                    name = "\"" + className + "\"";
                }
                else {
                    name = "com.ichess.jvoodoo.Voodoo.getInstanceName(this)";
                }
                body.append("{ " +
                        debugLine +
                        returnStatement +
                        castResult +
                        invocationMethod +
                        "( " + name + ", \"" + actualMethod.getName() + "\", $args ) " +
                        "; }");
                actualMethod.setBody(body.toString());
                setPublic(actualMethod);
                actualMethod.setModifiers(actualMethod.getModifiers() & (~java.lang.reflect.Modifier.ABSTRACT));
                LOGGER.fine("new method : '" + ctClass.getName() + ":" + actualMethod.getName() + " : modifiers " +
                        Integer.toHexString(actualMethod.getModifiers()) + " : " + body + "'");
            }

            if (baseClass != ctClass)
            {
                LOGGER.fine("Writing Class " + baseClass.getName() + " with modifiers " + Integer.toHexString(baseClass.getModifiers()));
                _voodooClasses.put(baseClass.getName(), baseClass.toClass());
            }
            ctClass.setModifiers(ctClass.getModifiers() & (~ java.lang.reflect.Modifier.INTERFACE));
            ctClass.setModifiers(ctClass.getModifiers() & (~ java.lang.reflect.Modifier.ABSTRACT));
            LOGGER.fine("Writing Class " + ctClass.getName() + " with modifiers " + Integer.toHexString(ctClass.getModifiers()));
            _voodooClasses.put(ctClass.getName(), ctClass.toClass());
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            Assert.fail("Could not cast jvoodoo on class '" + className + "'");
        }
        LOGGER.fine("Casted jvoodoo on class " + className);
    }
}
