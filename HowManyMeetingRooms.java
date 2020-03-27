package tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HowManyMeetingRooms {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

    @Test
    public void test() throws Exception {
        Assertions.assertEquals(3, countMeetingRooms(
                Arrays.asList(
                        new Interval("26/03/2020 01:30 PM ", "26/03/2020 02:30 PM"),
                        new Interval("26/03/2020 11:20 AM ", "26/03/2020 11:50 AM"),
                        new Interval("26/03/2020 10:30 AM ", "26/03/2020 4:30 PM"),
                        new Interval("26/03/2020 01:30 PM ", "26/03/2020 02:30 PM"),
                        new Interval("26/03/2020 02:30 PM ", "26/03/2020 03:30 PM"),
                        new Interval("26/03/2020 09:30 AM ", "26/03/2020 10:30 AM"))));

        Assertions.assertEquals(1, countMeetingRooms(
                Arrays.asList(
                        new Interval("26/03/2020 01:30 PM ", "26/03/2020 02:30 PM"),
                        new Interval("26/03/2020 06:20 PM ", "27/03/2020 11:50 AM"))));

        Assertions.assertEquals(2, countMeetingRooms(
                Arrays.asList(
                        new Interval("26/03/2020 01:30 PM ", "26/03/2020 02:30 PM"),
                        new Interval("26/03/2020 02:00 PM ", "27/03/2020 02:30 PM"),
                        new Interval("26/03/2020 06:20 PM ", "27/03/2020 11:50 AM"))));
        try {
            countMeetingRooms(Arrays.asList(new Interval("26/03/2020 01:30 PM ", "26/03/2020 02:30 AM")));
            Assertions.fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ignore) {}
    }

    // Time complexity - O(nlogn)
    private int countMeetingRooms(List<Interval> intervals) {
        intervals.sort(new ByStartTime()); // O(nlogn)

        PriorityQueue<Interval> queue = new PriorityQueue<>(new ByEndTime());
        queue.add(intervals.get(0)); // O(logn)
        int minRequired = Integer.MIN_VALUE;
        for (int i = 1; i < intervals.size(); i++) { //O(nlogn)
            // Check if interval with earlier ending time is less than start time of current interval
            while (!queue.isEmpty() && intervals.get(i).getStart() >= queue.peek().getEnd())  //O(C) or O(1)
                queue.remove(); // O(1)

            queue.add(intervals.get(i)); // O(logn)
            minRequired = Math.max(minRequired, queue.size());
        }
        return minRequired;
    }

    private static class Interval {
        private long start;
        private long end;

        Interval(String start, String end) throws ParseException {
            this.start = parseDate(start);
            this.end = parseDate(end);
            if (this.start >= this.end) throw new IllegalArgumentException("Start date should be older then end date");
        }

        private long parseDate(String date) throws ParseException {
            return DATE_FORMAT.parse(date).getTime();
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }
    }

    private static class ByStartTime implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            return Long.compare(o1.getStart(), o2.getStart());
        }
    }

    private static class ByEndTime implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            return Long.compare(o1.getEnd(), o2.getEnd());
        }
    }
}
