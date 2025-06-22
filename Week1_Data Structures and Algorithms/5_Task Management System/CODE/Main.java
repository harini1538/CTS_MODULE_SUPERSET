class Task {
    int taskId;
    String taskName;
    String status;
    Task next;

    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.next = null;
    }

    @Override
    public String toString() {
        return "ID: " + taskId + ", Name: " + taskName + ", Status: " + status;
    }
}

class TaskList {
    private Task head;

    // Add task at end
    public void addTask(int taskId, String taskName, String status) {
        Task newTask = new Task(taskId, taskName, status);
        if (head == null) {
            head = newTask;
        } else {
            Task current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newTask;
        }
        System.out.println("Task added.");
    }

    // Search task by ID
    public void searchTask(int taskId) {
        Task current = head;
        while (current != null) {
            if (current.taskId == taskId) {
                System.out.println("Task found: " + current);
                return;
            }
            current = current.next;
        }
        System.out.println("Task not found.");
    }

    // Delete task by ID
    public void deleteTask(int taskId) {
        if (head == null) {
            System.out.println("No tasks to delete.");
            return;
        }

        if (head.taskId == taskId) {
            head = head.next;
            System.out.println("Task deleted.");
            return;
        }

        Task current = head;
        while (current.next != null && current.next.taskId != taskId) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }

    // Traverse and print all tasks
    public void displayTasks() {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }
        Task current = head;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TaskList taskList = new TaskList();

        taskList.addTask(1, "Prepare report", "Pending");
        taskList.addTask(2, "Fix bugs", "In Progress");
        taskList.addTask(3, "Code review", "Pending");

        System.out.println("\nAll Tasks:");
        taskList.displayTasks();

        System.out.println("\nSearch Task ID 2:");
        taskList.searchTask(2);

        System.out.println("\nDelete Task ID 1:");
        taskList.deleteTask(1);

        System.out.println("\nTasks After Deletion:");
        taskList.displayTasks();
    }
}
