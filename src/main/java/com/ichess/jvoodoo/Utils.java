package com.ichess.jvoodoo;

import java.util.logging.Logger;

/**
 * Created by Ran on 26/08/2015.
 */
public class Utils {
    public final static Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static void sleep(int ms)
    {
        try {
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException ex) {

        }
    }
}
