package com.jcs.todomvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/todos")
public class TodosController {

    private Set<Todo> todos = new HashSet<>();

    @RequestMapping(method = GET)
    public HttpEntity<Collection<ResourceWithUrl>> listAll() {
        List<ResourceWithUrl> resourceWithUrls = todos.stream().map(todo -> toResource(todo)).collect(Collectors.toList());
        return new ResponseEntity<>(resourceWithUrls, OK);
    }

    @RequestMapping(value = "/{todo-title}", method = GET)
    public HttpEntity<ResourceWithUrl> getTodo(@PathVariable("todo-title") String title ) {

        Optional<Todo> todoOptional = todos.stream().filter(todo -> todo.getTitle().equals(title)).findFirst();

        if (!todoOptional.isPresent())
            return new ResponseEntity<>(NOT_FOUND);

        return respondeWithResource(todoOptional.get(), OK);
    }

    private String getHref(Todo todo) {
        return linkTo( methodOn(this.getClass()).getTodo(todo.getTitle()) ).withSelfRel().getHref();
    }

    @RequestMapping(method = POST,  headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl> saveTodo(@RequestBody Todo todo) {
        todos.add(todo);

        return respondeWithResource(todo, CREATED);
    }

    private HttpEntity<ResourceWithUrl> respondeWithResource(Todo todo, HttpStatus statusCode) {
        ResourceWithUrl resourceWithUrl = toResource(todo);

        return new ResponseEntity<>(resourceWithUrl, statusCode);
    }

    private ResourceWithUrl toResource(Todo todo) {
        return new ResourceWithUrl(todo, getHref(todo));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllTodos() {
        todos.clear();
    }

}
