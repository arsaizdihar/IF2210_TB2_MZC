package mzc.app.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.price.IPrice;

import java.math.BigDecimal;

@Entity
@Table(name = "producthistory")
@Getter
@Setter
@NoArgsConstructor
public class ProductHistory extends BaseModel {
    @EqualCheck
    @Column
    private String name;

    @EqualCheck
    @Column
    private BigDecimal price;

    @EqualCheck
    @Column
    private BigDecimal buyPrice;

    @Column
    private String category;

    @Column
    private String image;

    @Column
    private Integer amount;

    @Setter(AccessLevel.NONE)
    @Column(name = "billId")
    private long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId", nullable = false, referencedColumnName = "id")
    private transient FixedBill bill;

    public ProductHistory(String name, BigDecimal price, BigDecimal buyPrice, String category, String image, Integer amount, FixedBill bill) {
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.image = image;
        this.amount = amount;
        setBill(bill);
    }

    public ProductHistory(Product product, FixedBill bill, Integer amount) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.buyPrice = product.getBuyPrice();
        this.category = product.getCategory();
        this.image = product.getImagePath();
        this.amount = amount;
        setBill(bill);
    }

    public void setBill(FixedBill bill) {
        this.bill = bill;
        billId = bill.getId();
    }

    public IPrice getPriceView() {
        return PriceFactory.createPriceView(this.price);
    }

    public IPrice getBuyPriceView() {
        return PriceFactory.createPriceView(this.buyPrice);
    }
}
