package br.com.challenge.dextra.api.controller.form;

/**
 * @param <T>
 * Interface para estipular um contrato de implementação, os próximos forms, devem implementar essa interface
 * e seus métodos.
 */
public interface Form<T> {
    T converter();

    T update(T object);
}
