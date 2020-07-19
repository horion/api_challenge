package br.com.challenge.dextra.api.controller.dto;

public class ErrorFormDTO extends Response{

    private static final long serialVersionUID = -7384048837745373568L;
    private String field;
    private String error;

    public ErrorFormDTO(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}