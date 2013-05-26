package nl.jworks.epub.logback;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 * <p/>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p/>
 * or (per the licensee's choosing)
 * <p/>
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */

public class SiftExample {

    public static void main(String[] args) throws JoranException {

        String configFile = "src/test/java/nl/jworks/epub/logback/sift-example.xml";

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        lc.reset();
        configurator.setContext(lc);
        configurator.doConfigure(configFile);


        Logger logger = LoggerFactory.getLogger(SiftExample.class);
        logger.debug("Application started");

        MDC.put("userid", "Erik");
        logger.debug("Alice says hello");

        //StatusPrinter.print(lc);
    }
}
