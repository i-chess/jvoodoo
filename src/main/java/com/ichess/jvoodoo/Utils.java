package com.ichess.jvoodoo;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Ran on 26/08/2015.
 */
public class Utils {
    public final static Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static final Map<String, String> PRIMITIVES_TO_WRAPPERS
            = new HashMap<String, String>();
    static
    {
        PRIMITIVES_TO_WRAPPERS.put(boolean.class.getName(), Boolean.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(byte.class.getName(), Byte.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(char.class.getName(), Character.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(double.class.getName(), Double.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(float.class.getName(), Float.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(int.class.getName(), Integer.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(long.class.getName(), Long.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(short.class.getName(), Short.class.getName());
        PRIMITIVES_TO_WRAPPERS.put(void.class.getName(), Void.class.getName());
    }

    public static void sleep(int ms)
    {
        try {
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException ex) {

        }
    }
}
