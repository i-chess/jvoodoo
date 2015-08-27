
package com.ichess.jvoodoo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by me on 7/6/14.
 */
public class QueueThatPassesSomethingToNotifier {

    private NotifierWithSoemthing notifier;
    private Something something;
    private List queue = new ArrayList();

    public QueueThatPassesSomethingToNotifier(NotifierWithSoemthing notifier) {
        this.notifier = notifier;
        this.something = new Something();
    }

    public QueueThatPassesSomethingToNotifier(NotifierWithSoemthing notifier, Something something) {
        this.notifier = notifier;
        this.something = something;
    }

    public void push(Object e) {
        queue.add(e);
    }

    public Object pop()
    {
        Object first = queue.remove(0);
        if ( queue.isEmpty())
        {
            notifier.notifyEmpty(something);
        }
        return first;
    }
}
