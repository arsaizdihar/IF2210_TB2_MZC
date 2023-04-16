package mzc.app.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ProductHistoryBill")
@Getter
@Setter @NoArgsConstructor
public class ProductHistoryBill extends BaseModel {
    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false)
    private long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private transient ProductHistory product;

    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false)
    private long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId", referencedColumnName = "id")
    private transient FixedBill bill;

    @Column
    private int amount;

    public void setProduct(ProductHistory product) {
        this.product = product;
        productId = product.getId();
    }

    public void setBill(FixedBill bill) {
        this.bill = bill;
        billId = bill.getId();
    }
}
