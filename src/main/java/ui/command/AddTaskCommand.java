package ui.command;

import data.TaskList;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

import java.util.ArrayList;

/**
 * @author Jiaaa-yang
 *
 * Command which adds a given task
 * to a given list.
 */
class AddTaskCommand extends TaskListCommand {

    public AddTaskCommand(String name, String args, TaskList taskList) {
        super(name, args, taskList);
    }

    @Override
    public boolean execute() throws IllegalArgumentException {
        TaskList taskList = this.getTaskList();
        Task newTask = createTask(super.getName(), super.getArgs());
        taskList.addTask(newTask);

        ArrayList<String> response = new ArrayList<>();
        response.add("The following new task has been added:");
        response.add(newTask.getDescription());
        response.add(String.format("You now have %d tasks!", taskList.getTaskListSize()));
        Command.styledPrint(response);
        return false;
    }

    /**
     * Creates a new task based on the name and args
     * of the command
     *
     * @param name Name of task to create
     * @param args Args to create task with
     * @return New Task created based on name and args
     * @throws IllegalArgumentException if args are invalid for task
     */
    private static Task createTask(String name, String args) throws IllegalArgumentException {
        Task task = null;
        if (args == null) {
            throw new IllegalArgumentException("Tasks description cannot be empty");
        }
        switch (name) {
        case "todo":
            task = new ToDo(args);
            break;
        case "deadline":
            if (!args.contains("/by")) {
                throw new IllegalArgumentException("Deadline requires a deadline!");
            }
            String[] deadlineInfo = args.split("/by", 2);
            String deadlineName = deadlineInfo[0].strip();
            String deadline = deadlineInfo[1].strip();
            task = new Deadline(deadlineName, deadline);
            break;
        case "event":
            if (!args.contains("/at")) {
                throw new IllegalArgumentException("Events requires a time!");
            }
            String[] eventInfo = args.split("/at", 2);
            String eventName = eventInfo[0].strip();
            String time = eventInfo[1].strip();
            task = new Event(eventName, time);
            break;
        }
        return task;
    }
}
