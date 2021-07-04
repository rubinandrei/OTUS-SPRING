package org.booklibrary.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class H2serverConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server(@Value("${app.port_server}") String port) throws SQLException {
        log.info("Start H2 TCP server on port " + port);
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", port);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer(@Value("${app.port_client}") String port) throws SQLException {
        log.info("Start H2 client on port " + port);
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", port);
    }

}
