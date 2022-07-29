package com.linkedin.learning.achieving.low.latency.edge.computing.vital.sign.alerts;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.query.*;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;

class EventControllerTest
{
    private ClientCache cache;
    private Region<String, VitalStat> region;
    private CqQuery cqQuery;

    private void init() throws CqException, RegionNotFoundException, CqExistsException
    {
        // init cache, region, and CQ

        // connect to the locator using default port 10334
        this.cache = connectToLocallyRunningGeode();


        // create a local region that matches the server region
        this.region = cache.<String, VitalStat>createClientRegionFactory(ClientRegionShortcut.PROXY)
                           .create("VitalStat");

        this.cqQuery = this.startCQ(this.cache, this.region);

        System.out.println("================"+this.cqQuery.getState()+"======");
    }

    private void run() throws InterruptedException {

        while(true){
            Thread.sleep(10000);
        }
    }

    private void close() throws CqException {

        // close the CQ and Cache
        this.cqQuery.close();
        this.cache.close();

    }


    public static void main(String[] args) {

        try{
            EventControllerTest mExample = new EventControllerTest();

            mExample.init();

            mExample.run();

            mExample.close();

            System.out.println("\n---- So that is CQ's----\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private CqQuery startCQ(ClientCache cache, Region region)
    throws CqException, RegionNotFoundException, CqExistsException {
        // Get cache and queryService - refs to local cache and QueryService

        CqAttributesFactory cqf = new CqAttributesFactory();
        cqf.addCqListener(new QAEventListener());
        CqAttributes cqa = cqf.create();

        String cqName = "vitalJunit";

        String queryStr = "select * from /VitalStat where (statName = 'respirationRate' and (value < 12 or value >  16))";
//        String queryStr = "select * from /VitalStat";

        QueryService queryService = region.getRegionService().getQueryService();
        CqQuery cqQuery = queryService.newCq(cqName, queryStr, cqa);
        cqQuery.execute();



        System.out.println("------- CQ is running\n");

        return cqQuery;
    }

    private ClientCache connectToLocallyRunningGeode() {

        PdxSerializer serializer = new ReflectionBasedAutoSerializer(".*");
        ClientCache cache = new ClientCacheFactory().addPoolLocator("127.0.0.1", 10334)
                                                    .setPdxSerializer(serializer)
                                                    .setPoolSubscriptionEnabled(true).set("log-level", "WARN").create();


        return cache;
    }

}