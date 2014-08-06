package com.jcs.todomvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
public class TodosController {

    private List<Todo> todos = new ArrayList<Todo>();

    @RequestMapping("/todos")
    public List<Todo> listAll() {
        return todos;
    }

}
