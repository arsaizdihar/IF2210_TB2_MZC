package mzc.app.utils.reactive;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BaseReactive<T> implements Property<T> {
    @Getter
    private T value;
    protected final @NotNull ArrayList<ChangeListener<? super T>> listeners = new ArrayList<>();
    protected final @NotNull ArrayList<InvalidationListener> invalidListeners = new ArrayList<>();
    private ObservableValue<? extends T> observable;
    private ChangeListener<? super T> listener;


    public BaseReactive(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        if (this.value != oldValue) {
            for (ChangeListener<? super T> listener : listeners) {
                listener.changed(this, oldValue, this.value);
            }
            for (InvalidationListener listener : invalidListeners) {
                listener.invalidated(this);
            }
        }
    }

    public void forceUpdate() {
        for (ChangeListener<? super T> listener : listeners) {
            listener.changed(this, this.value, this.value);
        }
        for (InvalidationListener listener : invalidListeners) {
            listener.invalidated(this);
        }
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
        invalidListeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidListeners.remove(listener);
    }

    @Override
    public void bind(ObservableValue<? extends T> observableValue) {
        if (observableValue == null) {
            throw new NullPointerException("Cannot bind to null");
        } else if (!observableValue.equals(this.observable)) {
            this.unbind();
            this.observable = observableValue;
            if (this.listener == null) {
                this.listener = (observable, oldValue, newValue) -> {
                    this.setValue(newValue);
                };
            }

            this.observable.addListener(this.listener);
            this.setValue(observableValue.getValue());
        }
    }

    @Override
    public void unbind() {
        if (this.observable != null) {
            this.observable.removeListener(this.listener);
            this.observable = null;
        }
    }

    @Override
    public boolean isBound() {
        return this.observable != null;
    }

    @Override
    public void bindBidirectional(Property<T> property) {
        Bindings.bindBidirectional(this, property);
    }

    @Override
    public void unbindBidirectional(Property<T> property) {
        Bindings.unbindBidirectional(this, property);
    }

    @Override
    public Object getBean() {
        return this.observable;
    }

    @Override
    public String getName() {
        return "";
    }
}
