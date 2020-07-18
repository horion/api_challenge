package br.com.challenge.dextra.api.controller.form;

public interface Form<T> {
    T converter();

    T update(T object);
}
