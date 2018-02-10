package com.gegf;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class MyConfig {

    @Bean
    public TransportClient client() throws Exception{
        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName("192.168.141.1"), 9300
        );

       /* Settings settings = Settings.builder()
                .put("cluster.name", "gegf").build();*/

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddress(node);
        return client;
    }
}
