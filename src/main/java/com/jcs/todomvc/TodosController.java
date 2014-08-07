package com.jcs.todomvc;

import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/todos",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public Todo saveTodo(@RequestBody Todo todo) {
        todos.add(todo);
        return todo;
    }

}
