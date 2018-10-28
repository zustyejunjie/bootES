package com.gegf;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class MyConfig {

    private String host = "172.16.14.174";
    private Integer port = 9300;

    @Bean
    public TransportClient client() throws Exception{
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch-dev").build();
        PreBuiltTransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));
        return client;
    }
}
