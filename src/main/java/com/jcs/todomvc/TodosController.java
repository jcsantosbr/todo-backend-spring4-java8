package com.jcs.todomvc;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RestController

public class TodosController {

    private List<Todo> todos = new ArrayList<Todo>();

    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public List<Todo> listAll() {
        return todos;
    }

    @RequestMapping(value = "/todos", method = RequestMethod.POST)
    public Todo saveTodo(Todo todo) {
        todos.add(todo);
        return todo;
    }

}
