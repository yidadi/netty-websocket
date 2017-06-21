package com.even.websocket.config;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.even.websocket.constant.WebSocketConstant;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@org.springframework.context.annotation.Configuration
public class SpringNettyConfig {
    @Value("${websocket.server.host}")
    private String host;

    @Value("${websocket.server.port}")
    private Integer port;
    @Value("${websocket.server.nodes}")
    private String nodes;
    @Bean
    public SocketIOServer server(Config config)
    {
        Configuration configuration = new Configuration();
        configuration.setHostname(host);
        configuration.setPort(port);
        RedissonClient redissonClient = Redisson.create(config);
        configuration.setStoreFactory(new RedissonStoreFactory(redissonClient));

        configuration.setAuthorizationListener(new AuthorizationListener() {
            public boolean isAuthorized(HandshakeData data) {
                //TODO
                return true;
            }
        });
        final SocketIOServer server = new SocketIOServer(configuration);
        server.addNamespace(WebSocketConstant.Namespace);
        return server;
    }


    @Bean
    public Config config(){
        Config config = new Config();
        Stream.of(nodes.split(",")).forEach(node->{
            config.useClusterServers().addNodeAddress(node);
        });
        return config;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}