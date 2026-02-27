package inc.skt.designingwithobjects.stripe.ratelimiter;

import java.util.*;
import java.util.stream.Collectors;

public class RatelimiterApp {
    public static void main(String[] args) {
        EventProcessor processor = new EventProcessor();
        List<Event> eventList = Arrays.asList(new Event("evt_1", "AcmeCorp", 100),
                new Event("evt_2", "AcmeCorp", 200),
                new Event("evt_3", "AcmeCorp", 500),
                new Event("evt_4", "AcmeCorp", 1200),
                new Event("evt_5", "CmeCorp", 1200),
                new Event("evt_5", "AmeCorp", 1300));
        List<EventStatus> results = processor.processEvents(eventList, 2, 1000);
        results.forEach(System.out::println);
        check(results.get(0).status, "ACCEPTED");
        check(results.get(1).status, "ACCEPTED");
        check(results.get(2).status, "DROPPED");
        check(results.get(3).status, "ACCEPTED");
        check(results.get(4).status, "ACCEPTED");

        System.out.println("--- All Tests Passed Successfully ---");
    }

    static void check(Object actual, Object expected) {
        if (!Objects.equals(actual, expected)) {
            throw new RuntimeException("Test Failed! Expected [" + expected + "] but got [" + actual + "]");
        }
        System.out.println("Assertion Passed: " + actual);
    }
}

class EventProcessor {
    public List<EventStatus> processEvents(List<Event> eventList, int limit, int windowSize) {
        Deque<Event> acceptedQueue = new ArrayDeque();
        List<Event> droppedEventList = new ArrayList<>();
        List<EventStatus> eventStatusList = new ArrayList<>();
        Map<String, Deque<Event>> merchantQueMap= new HashMap<>();

        for(Event event: eventList) {
            String merchantId = event.merchantId;
            if(!merchantQueMap.containsKey(merchantId)) {
                merchantQueMap.put(merchantId, new ArrayDeque<>());
            }
            acceptedQueue = merchantQueMap.get(merchantId);

            if(acceptedQueue.isEmpty()) {
                acceptedQueue.addLast(event);
                eventStatusList.add(new EventStatus(event, "ACCEPTED"));
                continue;
            }

            int windowStart = event.timeStamp - windowSize;
            while (!acceptedQueue.isEmpty() && acceptedQueue.peekFirst().timeStamp <= windowStart) {
                acceptedQueue.removeFirst();
            }

            if (acceptedQueue.size() < limit) {
                acceptedQueue.addLast(event); // Always add to the end
                eventStatusList.add(new EventStatus(event, "ACCEPTED"));
            } else {
                droppedEventList.add(event);
                eventStatusList.add(new EventStatus(event, "DROPPED"));
            }

            /*
            int eventTimeStamp = event.timeStamp;
            Event lastProcessedEvent = acceptedQueue.peek();
            int lastProcessedEventTimeStamp = lastProcessedEvent.timeStamp;
            System.out.println(eventTimeStamp - lastProcessedEventTimeStamp);
            int lag = eventTimeStamp - lastProcessedEventTimeStamp;
            // check the last processed event timestamp
            if(lag < windowSize) {
                if(acceptedQueue.size() == limit) {
                    // if already max events processed in window then drop the event
                    droppedEventList.add(event);
                    eventStatusList.add(new EventStatus(event, "DROPPED"));
                } else {
                    // add event
                    acceptedQueue.add(event);
                    eventStatusList.add(new EventStatus(event, "ACCEPTED"));
                }
            } else {
                // if queue size is 2 remove from front(first)
                if(acceptedQueue.size() == limit) {
                    acceptedQueue.removeFirst();
                }
                acceptedQueue.add(event);
                eventStatusList.add(new EventStatus(event, "ACCEPTED"));
            }*/
        }
        return eventStatusList;
    }
}

class EventStatus {
    Event event;
    String status;

    public EventStatus(Event event, String status) {
        this.event = event;
        this.status = status;
    }

    @Override
    public String toString() {
        return "EventStatus{" +
                "event=" + event +
                ", status='" + status + '\'' +
                '}';
    }
}
class Event {
    String eventId;
    String merchantId;
    int timeStamp;

    public Event(String eventId, String merchantId, int timeStamp) {
        this.eventId = eventId;
        this.merchantId = merchantId;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
