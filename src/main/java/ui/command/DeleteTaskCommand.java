package ui.command;

import data.TaskList;
import task.Task;

import java.util.ArrayList;

/**
 * Command which deletes a previously
 * added task.
 */
class DeleteTaskCommand extends TaskListCommand {

    public DeleteTaskCommand(String name, String args, TaskList taskList) {
        super(name, args, taskList);
    }

    @Override
    public boolean execute() throws IllegalArgumentException {
        // Args for this command represents index of task to delete
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(super.getArgs()) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Non-number passed to delete task");
        }


        TaskList taskList = this.getTaskList();
        Task deletedTask = taskList.deleteTask(taskIndex);

        ArrayList<String> response = new ArrayList<>();
        response.add("Noted. The following task has been deleted:");
        response.add(deletedTask.getDescription());
        response.add(String.format("You now have %d tasks!", taskList.getTaskListSize()));
        Command.styledPrint(response);
        return false;
    }
}
