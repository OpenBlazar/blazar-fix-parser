package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.tracker.TrackerData;
import com.blazarquant.bfp.database.util.DatabaseTestBase;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class TrackerDAOTest extends DatabaseTestBase {

    @Test
    public void testSelectTrackerData() {
        List<TrackerData> trackerDataList = trackerDAO.findTrackerData();
        assertEquals(1, trackerDataList.size());

        TrackerData trackerData = trackerDataList.get(0);
        assertEquals(14, trackerData.getMessageNumber());
        assertEquals(Instant.parse("2016-04-16T21:12:38Z"), trackerData.getParseDate());
    }

    @Test
    public void testInsertParse() {
        List<TrackerData> trackerDataList = trackerDAO.findTrackerData();
        assertEquals(1, trackerDataList.size());

        Instant time = Instant.parse("2016-06-06T16:16:16Z");
        int messageNumber = 15;
        trackerDAO.saveInputParse(time, messageNumber);

        trackerDataList = trackerDAO.findTrackerData();
        assertEquals(2, trackerDataList.size());

        TrackerData trackerData = trackerDataList.get(1);
        assertEquals(15, trackerData.getMessageNumber());
        assertEquals(time, trackerData.getParseDate());
    }

}
