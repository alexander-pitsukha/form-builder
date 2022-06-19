package ca.caserm.formbuilder.controller.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ResponseWrapper<E> {

    private E responseEntity;

    private Set<String> errors;

    private Set<String> messages;

    private String error;

}
