package mzc.app.utils.reactive;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class State<T> implements ObservableValue<T> {
    @Getter
    private T value;

    private final @NotNull ArrayList<ChangeListener<? super T>> listeners = new ArrayList<>();

    public State(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        if (!this.value.equals(value)) {
            for (ChangeListener<? super T> listener : listeners) {
                listener.changed(this, this.value, value);
            }
        }
        this.value = value;
    }

    public T setValue(Function<T, T> generator) {
        T value = generator.apply(this.value);
        setValue(value);
        return value;
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        listeners.remove(listener);
    }
    @Override
    public void addListener(InvalidationListener listener) {
    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
