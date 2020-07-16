package com.bernardocoferre.tcp.server;

import com.bernardocoferre.tcp.concerns.FrameType;
import com.bernardocoferre.tcp.model.Message;
import org.apache.mina.core.buffer.IoBuffer;

public class AckResponseBuilder extends ResponseBuilder {

    @Override
    public FrameType frame() {
        return FrameType.ACK;
    }

    @Override
    public IoBuffer build(Message message) throws Exception {
        IoBuffer dateBuffer = IoBuffer.allocate(0);
        dateBuffer.setAutoExpand(true);

        dateBuffer.flip();

        return dateBuffer;
    }

}
