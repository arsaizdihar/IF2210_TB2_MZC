package mzc.app.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

@Entity(name = "productbill")
@Table(name = "productbill")
@Getter
@Setter
@NoArgsConstructor
public class ProductBill extends BaseModel {
    @EqualCheck
    @Setter(AccessLevel.NONE)
    @Column(name = "productId")
    private long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private transient Product product;

    @EqualCheck
    @Setter(AccessLevel.NONE)
    @Transient
    private long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId", referencedColumnName = "id")
    private transient Bill bill;

    @EqualCheck
    @Column
    private int amount;

    public void setProduct(Product product) {
        this.product = product;
        productId = product.getId();
    }

    public void setBill(Bill bill) {
        this.bill = bill;
        billId = bill.getId();
    }
}
