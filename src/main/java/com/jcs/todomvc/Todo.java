package com.jcs.todomvc;

public class Todo {

    private String title;
    private Boolean completed;

    public Todo() {
    }

    public Todo(String title) {
        this.title = title;
    }

    public Todo(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (!title.equals(todo.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    public boolean isCompleted() {
        return nonNull(completed,false);
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Todo merge(Todo newTodo) {
        return new Todo( nonNull(newTodo.title, this.title), nonNull(newTodo.completed, this.completed)  );
    }

    private <T> T nonNull(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
