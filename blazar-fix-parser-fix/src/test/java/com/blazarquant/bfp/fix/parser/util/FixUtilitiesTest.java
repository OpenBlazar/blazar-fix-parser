package com.blazarquant.bfp.fix.parser.util;

import com.blazarquant.bfp.fix.data.FixField;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.data.FixPair;
import com.blazarquant.bfp.fix.data.FixValue;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class FixUtilitiesTest {

    private static final String SENDING_TIME = "2015/05/05 05:05:05";
    private static final String SENDER = "SENDER";
    private static final String TARGET = "TARGET";

    private final FixMessageFactory messageFactory = new FixMessageFactory();

    @Test
    public void testSendingTimeUtilities() {
        FixMessage actualMessage_1 = messageFactory.createFixMessage(SENDING_TIME, SENDER, TARGET);
        FixMessage actualMessage_2 = new FixMessage.Builder().build();

        assertEquals(SENDING_TIME, FixUtilities.getSendingTime(actualMessage_1));
        assertEquals("Unknown", FixUtilities.getSendingTime(actualMessage_2));
    }

    @Test
    public void testSenderUtilities() {
        FixMessage actualMessage_1 = messageFactory.createFixMessage(SENDING_TIME, SENDER, TARGET);
        FixMessage actualMessage_2 = new FixMessage.Builder().build();

        assertEquals(SENDER, FixUtilities.getSender(actualMessage_1));
        assertEquals("Unknown", FixUtilities.getSender(actualMessage_2));
    }

    @Test
    public void testReceiverUtilities() {
        FixMessage actualMessage_1 = messageFactory.createFixMessage(SENDING_TIME, SENDER, TARGET);
        FixMessage actualMessage_2 = new FixMessage.Builder().build();

        assertEquals(TARGET, FixUtilities.getReceiver(actualMessage_1));
        assertEquals("Unknown", FixUtilities.getReceiver(actualMessage_2));
    }

    @Test
    public void testOrdStatusUtilities() {
        FixMessage actualMessage_1 = messageFactory.createFixMessage(
                SENDING_TIME, SENDER, TARGET, new FixPair(39, new FixField(39, "OrdStatus"), new FixValue("4", "CANCELED")));
        FixMessage actualMessage_2 = new FixMessage.Builder().build();

        assertEquals("4", FixUtilities.getOrdStatus(actualMessage_1));
        assertEquals("CANCELED", FixUtilities.getOrdStatusDescription(actualMessage_1));

        assertEquals("", FixUtilities.getOrdStatus(actualMessage_2));
        assertEquals("", FixUtilities.getOrdStatusDescription(actualMessage_2));
    }

}
