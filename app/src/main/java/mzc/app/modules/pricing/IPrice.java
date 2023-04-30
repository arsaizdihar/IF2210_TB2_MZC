package mzc.app.modules.pricing;

import lombok.NonNull;

import java.math.BigDecimal;

public interface IPrice {
    public @NonNull BigDecimal getValue();

    public String toString();
}
