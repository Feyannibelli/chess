package edu.austral.dissis.chess.engine.model.result;

import java.util.function.Function;
import java.util.function.Consumer;
import java.util.Optional;

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

    // Métodos funcionales para mejor composición
    default <U> Result<U> map(Function<T, U> mapper) {
        if (isSuccess()) {
            return Result.success(mapper.apply(getValue()));
        }
        return Result.error(getErrorMessage());
    }

    default <U> Result<U> flatMap(Function<T, Result<U>> mapper) {
        if (isSuccess()) {
            return mapper.apply(getValue());
        }
        return Result.error(getErrorMessage());
    }

    default Result<T> ifSuccess(Consumer<T> action) {
        if (isSuccess()) {
            action.accept(getValue());
        }
        return this;
    }

    default Result<T> ifError(Consumer<String> action) {
        if (isError()) {
            action.accept(getErrorMessage());
        }
        return this;
    }

    default Optional<T> toOptional() {
        return isSuccess() ? Optional.of(getValue()) : Optional.empty();
    }

    default T getOrElse(T defaultValue) {
        return isSuccess() ? getValue() : defaultValue;
    }

    default T getOrElseGet(Function<String, T> supplier) {
        return isSuccess() ? getValue() : supplier.apply(getErrorMessage());
    }
}