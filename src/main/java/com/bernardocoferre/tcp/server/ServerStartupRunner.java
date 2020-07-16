package com.bernardocoferre.tcp.server;

import com.bernardocoferre.tcp.config.Parameters;
import com.bernardocoferre.tcp.server.codec.MessageCodecFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@Order(1)
public class ServerStartupRunner implements ApplicationRunner {

    @Autowired
    private ServerHandler handler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IoAcceptor acceptor = new NioSocketAcceptor();

        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessageCodecFactory()));

        acceptor.setHandler(this.handler);
        acceptor.bind(new InetSocketAddress(Parameters.PORT));
    }

}
