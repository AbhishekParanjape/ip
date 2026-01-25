package Dhoni;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class FindTest {
    private TaskList taskList;
    private Event event1;
    private Event event2;
    private Deadline deadline1;
    private Todo todo1;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        event1 = new Event("project meeting", "2024-08-06", "2024-08-08");
        event2 = new Event("team lunch", "2024-08-10", "2024-08-10");
        deadline1 = new Deadline("return book", "2024-06-06");
        todo1 = new Todo("read book");
    }

    @Test
    @DisplayName("find task by deadline date")
    void findTaskByDeadlineDate() {
        taskList.addTask(deadline1);
        taskList.addTask(event1);

        LocalDate targetDate = LocalDate.parse("2024-06-06");
        List<Task> foundTasks = taskList.findByDate(targetDate);

        assertEquals(1, foundTasks.size(), "Should find 1 task");
        assertEquals(deadline1, foundTasks.get(0), "Should find the deadline task");
    }

    @Test
    @DisplayName("find task by event date within range")
    void findTaskByEventDateWithinRange() {
        taskList.addTask(event1);

        LocalDate targetDate = LocalDate.parse("2024-08-07");
        List<Task> foundTasks = taskList.findByDate(targetDate);

        assertEquals(1, foundTasks.size(), "Should find 1 event");
        assertEquals(event1, foundTasks.get(0), "Should find the event within date range");
    }

    @Test
    @DisplayName("find task by event start date")
    void findTaskByEventStartDate() {
        taskList.addTask(event1);

        LocalDate startDate = LocalDate.parse("2024-08-06");
        List<Task> foundTasks = taskList.findByDate(startDate);

        assertEquals(1, foundTasks.size(), "Should find event on start date");
    }

    @Test
    @DisplayName("find task by event end date")
    void findTaskByEventEndDate() {
        taskList.addTask(event1);

        LocalDate endDate = LocalDate.parse("2024-08-08");
        List<Task> foundTasks = taskList.findByDate(endDate);

        assertEquals(1, foundTasks.size(), "Should find event on end date");
    }

    @Test
    @DisplayName("find returns empty list when no tasks match")
    void findReturnsEmptyWhenNoMatch() {
        taskList.addTask(event1);
        taskList.addTask(deadline1);

        LocalDate targetDate = LocalDate.parse("2024-12-25");
        List<Task> foundTasks = taskList.findByDate(targetDate);

        assertEquals(0, foundTasks.size(), "Should return empty list");
    }

    @Test
    @DisplayName("find returns multiple tasks on same date")
    void findReturnsMultipleTasksOnSameDate() {
        Event event3 = new Event("workshop", "2024-08-06", "2024-08-07");
        taskList.addTask(event1);
        taskList.addTask(event3);

        LocalDate targetDate = LocalDate.parse("2024-08-06");
        List<Task> foundTasks = taskList.findByDate(targetDate);

        assertEquals(2, foundTasks.size(), "Should find 2 events on 2024-08-06");
    }

    @Test
    @DisplayName("find does not return event outside date range")
    void findDoesNotReturnEventOutsideRange() {
        taskList.addTask(event1);

        LocalDate outsideDate = LocalDate.parse("2024-08-09");
        List<Task> foundTasks = taskList.findByDate(outsideDate);

        assertEquals(0, foundTasks.size(), "Should not find event outside range");
    }

    @Test
    @DisplayName("find ignores todo tasks")
    void findIgnoresTodoTasks() {
        taskList.addTask(todo1);
        taskList.addTask(deadline1);

        LocalDate targetDate = LocalDate.parse("2024-06-06");
        List<Task> foundTasks = taskList.findByDate(targetDate);

        assertEquals(1, foundTasks.size(), "Should only find deadline, not todo");
        assertEquals(deadline1, foundTasks.get(0));
    }
}