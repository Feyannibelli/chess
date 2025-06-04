package edu.austral.dissis.chess.engine.model.result;

public sealed interface Result<T> permits Success, Error {

    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Result<T> error(String message) {
        return new Error<>(message);
    }

    boolean isSuccess();
    boolean isError();
    T getValue();
    String getErrorMessage();
}