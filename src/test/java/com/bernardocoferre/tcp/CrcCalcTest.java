package com.bernardocoferre.tcp;

import com.bernardocoferre.tcp.model.MessageData;
import com.bernardocoferre.tcp.support.Crc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CrcCalcTest {

    @Test
    public void whenTextMessage_thenCheckCrc() throws Exception {
        MessageData data = new MessageData();
        data.setInit("0A");
        data.setBytes("05");
        data.setFrame("A0");
        data.setData("");
        data.setCrc(Crc.fromMessageData(data));
        data.setEnd("0D");

        assertThat(data.getCrc())
                .isEqualTo("28");
    }

    @Test
    public void whenUserMessage_thenCheckCrc() throws Exception {
        MessageData data = new MessageData();
        data.setInit("0A");
        data.setBytes("05");
        data.setFrame("A0");
        data.setData("");
        data.setCrc(Crc.fromMessageData(data));
        data.setEnd("0D");

        assertThat(data.getCrc())
                .isEqualTo("28");
    }

    @Test
    public void whenDateMessage_thenCheckCrc() throws Exception {
        MessageData data = new MessageData();
        data.setInit("0A");
        data.setBytes("0B");
        data.setFrame("A3");
        data.setData("11 06 14 11 2B 0F");
        data.setCrc(Crc.fromMessageData(data));
        data.setEnd("0D");

        assertThat(data.getCrc())
                .isEqualTo("FE");
    }

}
