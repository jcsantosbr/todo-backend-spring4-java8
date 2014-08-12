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

        return respondeWithResource(todoOptional.get(), HttpStatus.OK);
    }

    private String getHref(Todo todo) {
        return linkTo( thisController.getTodo(todo.getTitle()) ).withSelfRel().getHref();
    }

    @RequestMapping(method = RequestMethod.POST,  headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl> saveTodo(@RequestBody Todo todo) {
        todos.add(todo);

        return respondeWithResource(todo, HttpStatus.CREATED);
    }

    private HttpEntity<ResourceWithUrl> respondeWithResource(Todo todo, HttpStatus statusCode) {
        ResourceWithUrl resourceWithUrl = new ResourceWithUrl(todo, getHref(todo));

        return new ResponseEntity<>(resourceWithUrl, statusCode);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllTodos() {
        todos.clear();
    }

}
