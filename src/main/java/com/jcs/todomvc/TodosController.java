package com.jcs.todomvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/todos")
public class TodosController {

    private List<Todo> todos = new ArrayList<Todo>();
    private TodosController thisController = methodOn(this.getClass());

    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> listAll() {
        return todos;
    }

    @RequestMapping(value = "/{todo-title}", method = RequestMethod.GET)
    public HttpEntity<ResourceWithUrl> getTodo(@PathVariable("todo-title") String title ) {

        Optional<Todo> todoOptional = todos.stream().filter(todo -> todo.getTitle().equals(title)).findFirst();

        if (!todoOptional.isPresent())
            return new ResponseEntity<ResourceWithUrl>(HttpStatus.NOT_FOUND);

        Todo todo = todoOptional.get();
        ResourceWithUrl<Todo> resource = new ResourceWithUrl<>(todo, getHref(todo));

        return new ResponseEntity<ResourceWithUrl>(resource, HttpStatus.CREATED);
    }

    private String getHref(Todo todo) {
        return linkTo( thisController.getTodo(todo.getTitle()) ).withSelfRel().getHref();
    }

    @RequestMapping(method = RequestMethod.POST,  headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl> saveTodo(@RequestBody Todo todo) {
        todos.add(todo);

        ResourceWithUrl resourceWithUrl = new ResourceWithUrl(todo, getHref(todo));

        return new ResponseEntity<ResourceWithUrl>(resourceWithUrl, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllTodos() {
        todos.clear();
    }

}
