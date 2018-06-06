package com.example.lohit.hw4.dummy;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HistoryContent {

    public static final List<HistoryItem> ITEMS = new ArrayList<>();
    public static void addItem(HistoryItem item) {
        ITEMS.add(item);
    }
    static {
        DateTime now = DateTime.now();
        addItem(new HistoryItem("43.145", "-85.127", "42.4545", "85.8585",
                now.minusDays(1)));
        addItem(new HistoryItem("42.123", "-77.741", "42.6363", "85.2525",
                now.minusDays(1)));
        addItem(new HistoryItem("22.152", "-78.896", "44.123", "33.487",
                now.plusDays(1)));
        addItem(new HistoryItem("14.3636", "74.858", "15.986", "15.145",
                now.plusDays(1)));
    }
    // match with inputs with 4 params
    public static class HistoryItem {

        public final String origLat;
        public final String origLng;
        public final String destLat;
        public final String destLng;
        public final DateTime timestamp;
        public HistoryItem(String origLat, String origLng, String destLat,
                           String destLng, DateTime timestamp) {
            this.origLat = origLat;
            this.origLng = origLng;
            this.destLat = destLat;
            this.destLng = destLng;
            this.timestamp = timestamp;
        }
        @Override
        public String toString() {
            return "(" + this.origLat + "," + this.origLat + ")";
        }
    }
}
