package com.jcs.todomvc;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping(value = "/todos", headers = {"Content-type=application/json"})
public class TodosController {

    private List<Todo> todos = new ArrayList<Todo>();

    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> listAll() {
        return todos;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Todo saveTodo(@RequestBody Todo todo) {
        todos.add(todo);
        return todo;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteTodo(@RequestBody Todo todo) {
        todos.remove(todo);
    }



}
