package edu.austral.dissis.chess.engine.util;

import java.util.Optional;
import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Success, Result.Error {

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> error(E error) {
        return new Error<>(error);
    }

    boolean isSuccess();

    boolean isError();

    Optional<T> getValue();

    Optional<E> getError();

    <U> Result<U, E> map(Function<T, U> mapper);

    <F> Result<T, F> mapError(Function<E, F> mapper);

    record Success<T, E>(T value) implements Result<T, E> {
        public Success {
            if (value == null) {
                throw new IllegalArgumentException("Success value cannot be null");
            }
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public Optional<T> getValue() {
            return Optional.of(value);
        }

        @Override
        public Optional<E> getError() {
            return Optional.empty();
        }

        @Override
        public <U> Result<U, E> map(Function<T, U> mapper) {
            return Result.success(mapper.apply(value));
        }

        @Override
        public <F> Result<T, F> mapError(Function<E, F> mapper) {
            return Result.success(value);
        }
    }

    record Error<T, E>(E error) implements Result<T, E> {
        public Error {
            if (error == null) {
                throw new IllegalArgumentException("Error value cannot be null");
            }
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public Optional<T> getValue() {
            return Optional.empty();
        }

        @Override
        public Optional<E> getError() {
            return Optional.of(error);
        }

        @Override
        public <U> Result<U, E> map(Function<T, U> mapper) {
            return Result.error(error);
        }

        @Override
        public <F> Result<T, F> mapError(Function<E, F> mapper) {
            return Result.error(mapper.apply(error));
        }
    }
}