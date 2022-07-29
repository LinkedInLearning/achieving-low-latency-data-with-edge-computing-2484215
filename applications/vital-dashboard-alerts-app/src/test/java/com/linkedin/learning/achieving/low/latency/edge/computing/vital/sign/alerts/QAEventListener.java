package com.linkedin.learning.achieving.low.latency.edge.computing.vital.sign.alerts;

import org.apache.geode.cache.query.CqEvent;
import org.apache.geode.cache.query.CqListener;
import org.apache.geode.cache.util.CqListenerAdapter;

/**
 * QAEventListener
 *
 * @author Gregory Green
 */
public class QAEventListener extends CqListenerAdapter
{
    public void onEvent(CqEvent aCqEvent) {
        System.out.println(aCqEvent);
    }

    @Override
    public void onError(CqEvent aCqEvent)
    {
        System.out.println("ERROR:"+aCqEvent);
    }
}
