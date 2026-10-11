package org.app.task;

public record DeleteTaskPayload(boolean success, Task task) {
}
