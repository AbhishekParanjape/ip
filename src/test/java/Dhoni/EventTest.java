package Dhoni;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @Test
    @DisplayName("toString() starts with [E] tag")
    void toString_hasTypePrefixE() {
        Event event = new Event("project meeting", "2024-08-06", "2024-08-08");
        String s = event.toString();
        assertNotNull(s, "toString() should not return null");
        assertTrue(s.startsWith("[E]"), "toString() should start with [E]");
    }

    @Test
    @DisplayName("check event date format parsing")
    void checkEventDateFormat() {
        Event event = new Event("project meeting", "2024-08-06", "2024-08-08");
        assertEquals("[E][ ] project meeting (from: Aug 06 2024 to: Aug 08 2024)", 
                event.toString(),
                "Expected formatted event with correct dates");
    }

    @Test
    @DisplayName("event toFileFormat() returns correct format")
    void checkEventFileFormat() {
        Event event = new Event("team lunch", "2024-08-10", "2024-08-12");
        assertEquals("E | 0 | team lunch | 2024-08-10 | 2024-08-12", 
                event.toFileFormat(),
                "Expected file format string");
    }

    @Test
    @DisplayName("event can be marked as completed")
    void eventMarkedAsCompleted() {
        Event event = new Event("workshop", "2024-09-01", "2024-09-02");
        event.completed();
        assertTrue(event.isDone, "Event should be marked as done");
        assertTrue(event.toString().contains("[X]"), "Completed event should show [X]");
    }
}