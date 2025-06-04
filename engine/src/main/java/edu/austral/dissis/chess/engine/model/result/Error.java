package edu.austral.dissis.chess.engine.model.result;

public record Error<T>(String message) implements Result<T> {

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public T getValue() {
        throw new UnsupportedOperationException("Error has no value");
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}