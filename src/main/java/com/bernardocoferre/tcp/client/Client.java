package com.bernardocoferre.tcp.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

import com.bernardocoferre.tcp.config.Parameters;
import com.bernardocoferre.tcp.server.codec.MessageCodecFactory;
import com.bernardocoferre.tcp.support.Str;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    /**
     * Start N clients and send data to server.
     *
     * @param amount clients amount
     */
    public void start(int amount) {
        for (int i = 0; i < amount; i++)
            this.start();
    }

    /**
     * Start a single client and send data to server.
     */
    public void start() {
        IoSession session = null;
        NioSocketConnector connector = new NioSocketConnector();

        try {
            // configures the socket connector
            this.configure(connector);

            session = this.connect(connector);
            this.write(session);

            // simulates a long data processing
            Thread.sleep(500);

        } catch (Exception e) {
            LOGGER.error("Failed to start client", e);
        } finally {
            this.close(connector, session);
        }
    }

    /**
     * Writes a fresh new hex message.
     *
     * @param session current session
     * @throws DecoderException when failed to parse the Hex bytes
     */
    private void write(final IoSession session) throws DecoderException {
        IoBuffer buffer = IoBuffer.allocate(11);
        buffer.setAutoExpand(true);

        String input = "Hello World";

        String init = "0A";
        String bytes = "10";
        String frame = "A1";
        String data = Hex.encodeHexString(input.getBytes(StandardCharsets.UTF_8));
        String crc = "DC";
        String end = "0D";

        buffer.put(Hex.decodeHex(init));
        buffer.put(Hex.decodeHex(bytes));
        buffer.put(Hex.decodeHex(frame));
        buffer.put(Hex.decodeHex(data));
        buffer.put(Hex.decodeHex(crc));
        buffer.put(Hex.decodeHex(end));

        buffer.flip();

        session.write(buffer);
    }

    /**
     * Configures the new connector.
     *
     * @param connector the connector instance
     */
    private void configure(final NioSocketConnector connector) {
        connector.setConnectTimeoutMillis(Parameters.CLIENT_TIMEOUT);

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessageCodecFactory()));

        connector.setHandler(new ClientHandler());
    }

    /**
     * Open a new session to the remote address.
     *
     * @param connector the connector instance
     * @return created session
     */
    private IoSession connect(final NioSocketConnector connector) {
        try {
            SocketAddress address = new InetSocketAddress(Parameters.HOST_ADDRESS, Parameters.PORT);
            ConnectFuture future = connector.connect(address);

            future.awaitUninterruptibly();
            return future.getSession();

        } catch (Exception e) {
            LOGGER.error(new Str.Builder("Failed to connect to server [{}:{}]").build(Parameters.HOST_ADDRESS, Parameters.PORT), e);
            throw e;
        }
    }

    /**
     * Closes the socket connection.
     *
     * @param connector the connector instance
     * @param session current session
     */
    private void close(final NioSocketConnector connector, final IoSession session) {
        if (session != null && session.isConnected())
            session.closeNow().awaitUninterruptibly();

        connector.dispose();
    }

}
