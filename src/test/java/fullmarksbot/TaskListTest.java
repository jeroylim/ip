package fullmarksbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for the TaskList class.
 */
class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    /**
     * Tests adding a task to the TaskList.
     */
    @Test
    void testAddTask() {
        Task task = new Todo("Eat");
        taskList.addTask(task);

        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    /**
     * Tests deleting tasks from the TaskList and handling invalid indices.
     */
    @Test
    void testDeleteTask() {
        Task task1 = new Todo("Write a lot");
        Task task2 = new Todo("Submit assignment");

        taskList.addTask(task1);
        taskList.addTask(task2);

        try {
            taskList.deleteTask(0);
        } catch (FullMarksException e) {
            fail("Deleting a valid task should not throw an exception");
        }

        assertEquals(1, taskList.size());
        assertEquals(task2, taskList.getTask(0));

        try {
            taskList.deleteTask(5);
            fail("Expected FullMarksException was not thrown");
        } catch (FullMarksException e) {
            assertEquals("Task number 6 does not exist.", e.getMessage());
        }
    }
}
