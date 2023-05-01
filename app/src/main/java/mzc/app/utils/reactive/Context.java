package mzc.app.utils.reactive;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public final class Context<T> extends BaseReactive<T> {

    @Getter
    private final @NotNull Class<T> clazz;

    @SuppressWarnings("unchecked")
    public Context(T value) {
        super(value);
        Class<?> clazz = value.getClass();
        this.clazz = (Class<T>) clazz;
    }
}
