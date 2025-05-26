package edu.austral.dissis.chess.engine.game;


public final class Result<T> {
    private final T value;
    private final boolean success;
    private final String error;

    private Result(T value, boolean success, String error) {
        this.value = value;
        this.success = success;
        this.error = error;
    }

    //genera un resultado con un valor en especifico
    public static <T> Result<T> success(T value) {
        return new Result<>(value, true, null);
    }

    //genera un fallo del resultado
    public static <T> Result<T> error(String error) {
        return new Result<>(null, false, error);
    }

    //consigue el resultado del valor
    public T getValue() {
        return value;
    }

    //verifica si el resultado es bueno
    public boolean isSuccess() {
        return success;
    }

    //consigue el mensaje de error
    public String getError() {
        return error;
    }

    //mapa del valor del resultado
    public <R> Result<R> map(java.util.function.Function<T, R> mapper) {
        if (success) {
            return Result.success(mapper.apply(value));
        } else {
            return Result.error(error);
        }
    }
}