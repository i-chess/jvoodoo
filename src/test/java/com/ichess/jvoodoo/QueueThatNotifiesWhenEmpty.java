
package com.ichess.jvoodoo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by me on 7/6/14.
 */
public class QueueThatNotifiesWhenEmpty {

    private EmptyNotifier notifier;
    private List<Object> queue = new ArrayList<Object>();

    public QueueThatNotifiesWhenEmpty( EmptyNotifier notifier) {
        this.notifier = notifier;
    }

    public void push(Object e) {
        queue.add(e);
    }

    public Object pop()
    {
        Object first = queue.remove(0);
        if ( queue.isEmpty())
        {
            notifier.notifyEmpty();
        }
        return first;
    }
}
