package com.bernardocoferre.tcp.concerns;

import com.bernardocoferre.tcp.model.MessageReference;
import org.apache.mina.core.buffer.IoBuffer;

public interface DetectsMessageType {

    /**
     * Typify a message into a custom sub-type.
     *
     * @param messageReference the message reference
     * @param data the buffer data
     */
    void typify(MessageReference messageReference, IoBuffer data);

}
