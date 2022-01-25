package ui.command;

import data.TaskList;
import task.Task;

import java.util.ArrayList;

/**
 * Command which searches among
 * the TaskList and returns
 * tasks matching a keyword.
 */
public class FindCommand extends TaskListCommand {

    public FindCommand(String name, String args, TaskList taskList) {
        super(name, args, taskList);
    }

    @Override
    public boolean execute() throws IllegalArgumentException {
        // Args for this command represents keyword to search
        String keyword = super.getArgs();
        if (keyword == null) {
            throw new IllegalArgumentException("No terms passed to find command!");
        }

        TaskList taskList = this.getTaskList();
        ArrayList<Task> searchedTasks = taskList.filterTasks(task -> {
            String taskName = task.getName();
            return taskName.contains(keyword);
        });

        ArrayList<String> response = new ArrayList<>();
        if (searchedTasks.size() != 0) {
            response.add("Here are the tasks matching your searched terms");
            // Prepend numbering to searched tasks list
            for (int i = 0; i < searchedTasks.size(); i++) {
                Task task = searchedTasks.get(i);
                String taskDescription = String.format("%d. %s", i + 1, task.getDescription());
                response.add(taskDescription);
            }
        } else {
            response.add("Sorry! No tasks matching your search was found!");
        }
        Command.styledPrint(response);
        return false;
    }
}
