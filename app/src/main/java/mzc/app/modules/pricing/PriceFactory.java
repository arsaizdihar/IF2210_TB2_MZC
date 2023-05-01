package mzc.app.modules.pricing;

import lombok.Getter;
import mzc.app.modules.pricing.price.IPrice;
import mzc.app.modules.pricing.price.RupiahPrice;

import java.math.BigDecimal;

public class PriceFactory {
    @Getter
    private Class<? extends IPrice> priceView = RupiahPrice.class;

    public IPrice createPriceView(BigDecimal value) {
        try {
            return priceView.getConstructor(BigDecimal.class).newInstance(value);
        } catch (Exception exception) {
            System.out.println("Failed to create view from default price view");
            return new RupiahPrice(value);
        }
    }

    public void setPriceView(Class<? extends IPrice> newClass) throws Exception {
        // try to create price view with given class
        newClass.getConstructor(BigDecimal.class).newInstance(new BigDecimal(0));

        priceView = newClass;
    }
}
