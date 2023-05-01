package mzc.app.utils.reactive;

import java.util.function.Function;

public class State<T> extends BaseReactive<T> {

    public State(T value) {
        super(value);
    }

    public void setValue(Function<T, T> generator) {
        T value = generator.apply(this.getValue());
        setValue(value);
    }
}
