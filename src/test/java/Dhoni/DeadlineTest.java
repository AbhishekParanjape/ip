package dhoni;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dhoni.tasks.Deadline;

public class DeadlineTest {
    @Test
    @DisplayName("toString() starts with [D] tag")
    void toString_hasTypePrefixD() {
        Deadline deadlineTask = new Deadline("read book", "2024-12-02");
        String s = deadlineTask.toString();
        assertNotNull(s, "toString() should not return null");
        assertTrue(s.startsWith("[D]"), "toString() should start with [D]");
    }

    @Test
    @DisplayName("check date format parsing")
    void checkDateFormat() {
        Deadline d = new Deadline("read book", "2024-12-02");
        assertEquals("[D][ ] read book (by: Dec 02 2024)", d.toString(),
                "Expected formatted deadline");
    }
}
