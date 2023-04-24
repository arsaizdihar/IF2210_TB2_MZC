package mzc.app.bootstrap;

import lombok.NonNull;

import java.math.BigDecimal;

public interface IPricePipeline {
    @NonNull PricePipelineType getType();
    @NonNull String getName();

    BigDecimal calculateNominal(BigDecimal input);

    BigDecimal calculateTotal(BigDecimal input);
}
