package edu.austral.dissis.chess.engine.model.result;

public record Success<T>(T value) implements Result<T> {

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getErrorMessage() {
        throw new UnsupportedOperationException("Success has no error message");
    }
}