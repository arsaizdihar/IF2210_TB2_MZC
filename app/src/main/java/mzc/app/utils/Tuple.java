package mzc.app.utils;

import lombok.Getter;

public class Tuple<X, Y> {
    @Getter
    public X x;

    @Getter
    public Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}