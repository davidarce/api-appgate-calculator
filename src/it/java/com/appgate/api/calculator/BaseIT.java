package com.appgate.api.calculator;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.junit5.Karate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@KarateOptions(tags = "~@ignore")
public class BaseIT implements InitializingBean {

    @LocalServerPort
    int port;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("local.server.port", String.valueOf(port));
    }

    @Karate.Test
    Karate testAll() {
        return Karate.run().relativeTo(getClass());
    }

}