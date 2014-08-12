package com.jcs.todomvc;

public class Todo {

    private String title;
    private boolean completed;

    public Todo() {
    }

    public Todo(String title) {
        this.title = title;
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
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
