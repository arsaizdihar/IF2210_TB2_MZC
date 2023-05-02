package mzc.app.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

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

    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false, name = "billId")
    private long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId", nullable = false, referencedColumnName = "id")
    private transient FixedBill bill;

    public ProductHistory(String name, BigDecimal price, BigDecimal buyPrice, String category, String image, FixedBill bill) {
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.image = image;
        setBill(bill);
    }

    public void setBill(FixedBill bill) {
        this.bill = bill;
        billId = bill.getId();
    }
}
