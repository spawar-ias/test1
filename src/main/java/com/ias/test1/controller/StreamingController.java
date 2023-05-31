package com.ias.test1.controller;


import com.van.logging.LoggingEventCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/streaming")
public class StreamingController {

    private static Logger logger =
            LogManager.getLogger(StreamingController.class);
    @GetMapping("/log2")
    public boolean StreamLogs2() throws InterruptedException {
        logger.info("Hello from Main!");
        Long started = System.currentTimeMillis();
        Long now = System.currentTimeMillis();

        // Loop for 15 secs
        while (now - started < TimeUnit.SECONDS.toMillis(15)) {
            logger.info("Another round through the loop!");
            logger.warn("This is a warning!");
            logger.error("And this is an error!!!");
            // Sleep for a few seconds before logging messages again so we don't produce too much data
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
            now = System.currentTimeMillis();
        }

        // If the program should exit at this point, call LoggingEventCache.shutDown() to shut down and clean up
        // background threads. Without this, the program will "hang" because a publishing thread (a user thread, not
        // daemon) is waiting for work in its thread pool.
        // DO NOT log any more content after calling this; This should be the last statement in the main thread.
        LoggingEventCache.shutDown();
        return true;
    }
}
