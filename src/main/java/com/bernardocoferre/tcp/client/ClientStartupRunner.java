package com.bernardocoferre.tcp.client;

import com.bernardocoferre.tcp.config.Parameters;
import com.bernardocoferre.tcp.server.ServerHandler;
import com.bernardocoferre.tcp.server.codec.MessageCodecFactory;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@Order(2)
public class ClientStartupRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientStartupRunner.class);

    @Autowired
    private Client client;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info(new Str.Builder("Starting [{}] clients..").build(Parameters.CLIENTS_AMOUNT));
        this.client.start(Parameters.CLIENTS_AMOUNT);
    }

}
