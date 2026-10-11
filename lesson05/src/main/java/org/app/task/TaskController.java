package org.app.task;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final List<Task> tasks = new ArrayList<>(List.of(
            new Task("Learn GraphQL", false),
            new Task("Build a GraphQL API", true),
            new Task("Test the API", false),
            new Task("Keep learning", false)
    ));

    @QueryMapping
    public List<Task> tasks() {
        return tasks;
    }

    @QueryMapping
    public Task task(@Argument("title") String title) {
        return findByTitle(title);
    }

    @MutationMapping
    public TaskPayload addTask(@Argument("title") String title) {
        Task task = new Task(title, false);
        tasks.add(task);
        return new TaskPayload(true, task);
    }

    @MutationMapping
    public TaskPayload updateTask(@Argument("title") String title,
                                  @Argument("newTitle") String newTitle,
                                  @Argument("completed") Boolean completed) {
        Task task = findByTitle(title);
        if (task == null) {
            return new TaskPayload(false, null);
        }
        if (newTitle != null) {
            task.setTitle(newTitle);
        }
        if (completed != null) {
            task.setCompleted(completed);
        }
        return new TaskPayload(true, task);
    }

    @MutationMapping
    public DeleteTaskPayload deleteTask(@Argument("title") String title) {
        Task task = findByTitle(title);
        if (task == null) {
            return new DeleteTaskPayload(false, null);
        }
        tasks.remove(task);
        return new DeleteTaskPayload(true, task);
    }

    private Task findByTitle(String title) {
        return tasks.stream()
                .filter(t -> t.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }
}
