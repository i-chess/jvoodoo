//==============================================================================
//            Copyright (c) 2009-2014 ichess.co.il
//
//This document contains confidential information which is protected by
//copyright and is proprietary to ichess.co.il. No part
//of this document may be used, copied, disclosed, or conveyed to another
//party without prior written consent of ichess.co.il.
//==============================================================================

package com.ichess.jvoodoo;

import java.util.ArrayList;
import java.util.List;

public class QueueThatNotifyWithParameters {

    private NotifierWithParameters notifier;
    private List<Object> queue = new ArrayList<Object>();

    public QueueThatNotifyWithParameters(NotifierWithParameters notifier) {
        this.notifier = notifier;
    }

    public void push(Object e) {
        queue.add(e);
        notifier.notifyQueue(notifier, queue.size());
    }

    public Object pop()
    {
        Object first = queue.remove(0);
        notifier.notifyQueue(notifier, queue.size());
        return first;
    }
}
