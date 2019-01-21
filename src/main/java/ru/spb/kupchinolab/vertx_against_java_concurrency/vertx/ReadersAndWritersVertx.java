package ru.spb.kupchinolab.vertx_against_java_concurrency.vertx;

public class ReadersAndWritersVertx {
    //TODO set instances
    /*
    DeploymentOptions options = new DeploymentOptions().setInstances(16);
    vertx.deployVerticle("com.mycompany.MyOrderProcessorVerticle", options);
    */

    //TODO use Future? to compose futures

    // TODO use setTimer
    /*
    long timerID = vertx.setTimer(1000, id -> {
        System.out.println("And one second later this is printed");
    });
    */

    /* TODO Asynchronous locks

    sd.getLock("mylock", res -> {
    if (res.succeeded()) {
    // Got the lock!
    Lock lock = res.result();

    // 5 seconds later we release the lock so someone else can get it

     vertx.setTimer(5000, tid -> lock.release());

      } else {
        // Something went wrong
      }
    });
     */

        /* TODO Asynchronous locks with timeout
sd.getLockWithTimeout("mylock", 10000, res -> {
  if (res.succeeded()) {
    // Got the lock!
    Lock lock = res.result();

  } else {
    // Failed to get lock
  }
});
         */

        /*
        TODO Asynchronous counters

        sd.getCounter("mycounter", res -> {
             if (res.succeeded()) {
             Counter counter = res.result();
             } else {
                // Something went wrong!
             }
        });
         */
}
