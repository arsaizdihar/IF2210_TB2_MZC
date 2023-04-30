package mzc.app.plugins.base;

import java.math.BigDecimal;

public interface IPriceViewModule {
    String asString(BigDecimal nominal);

    BigDecimal asNumeric(BigDecimal nominal);
}
