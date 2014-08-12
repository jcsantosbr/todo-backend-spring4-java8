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
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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

        Optional<Todo> todoOptional = tryToFindByTitle(title);

        if (!todoOptional.isPresent())
            return new ResponseEntity<>(NOT_FOUND);

        return respondeWithResource(todoOptional.get(), OK);
    }

    private Optional<Todo> tryToFindByTitle(String title) {
        return todos.stream().filter(todo -> todo.getTitle().equals(title)).findFirst();
    }

    @RequestMapping(method = POST,  headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl> saveTodo(@RequestBody Todo todo) {
        todos.add(todo);

        return respondeWithResource(todo, CREATED);
    }

    @RequestMapping(method = DELETE)
    public void deleteAllTodos() {
        todos.clear();
    }

    @RequestMapping(value = "/{todo-title}",method = DELETE)
    public void deleteOneTodo(@PathVariable("todo-title") String title) {
        Optional<Todo> todoOptional = tryToFindByTitle(title);

        if ( todoOptional.isPresent() ) {
            todos.remove(todoOptional.get());
        }
    }

    @RequestMapping(value = "/{todo-title}",method = PATCH,  headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl> updateTodo(@PathVariable("todo-title") String title, @RequestBody Todo newTodo ) {
        Optional<Todo> todoOptional = tryToFindByTitle(title);

        if ( !todoOptional.isPresent() ) {
            return new ResponseEntity<>(NOT_FOUND);
        } else if ( newTodo == null ) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        todos.remove(todoOptional.get());

        Todo mergedTodo = todoOptional.get().merge(newTodo);
        todos.add(mergedTodo);

        return respondeWithResource(mergedTodo, OK);
    }


    private String getHref(Todo todo) {
        return linkTo( methodOn(this.getClass()).getTodo(todo.getTitle()) ).withSelfRel().getHref();
    }

    private ResourceWithUrl toResource(Todo todo) {
        return new ResourceWithUrl(todo, getHref(todo));
    }

    private HttpEntity<ResourceWithUrl> respondeWithResource(Todo todo, HttpStatus statusCode) {
        ResourceWithUrl resourceWithUrl = toResource(todo);

        return new ResponseEntity<>(resourceWithUrl, statusCode);
    }
}
