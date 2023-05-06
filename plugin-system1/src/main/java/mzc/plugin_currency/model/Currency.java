package mzc.plugin_currency.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;
import mzc.app.model.BaseModel;

import java.math.BigDecimal;

@Entity(name = "currency")
@Table(name = "currency")
@Getter
@Setter
@NoArgsConstructor
public class Currency extends BaseModel {
    @EqualCheck
    @Column
    private String symbol = "";

    @EqualCheck
    @Column
    private String name = "";

    @EqualCheck
    @Column
    private BigDecimal conversion = new BigDecimal(0);

    @EqualCheck
    @Column
    private boolean isDefaultCurrency = false;

    public Currency(String symbol, String name, BigDecimal conversion) {
        this.symbol = symbol;
        this.name = name;
        this.conversion = conversion;
    }

    public Currency(String symbol, String name, BigDecimal conversion, boolean isDefaultCurrency) {
        this.symbol = symbol;
        this.name = name;
        this.conversion = conversion;
        this.isDefaultCurrency = isDefaultCurrency;
    }

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}
