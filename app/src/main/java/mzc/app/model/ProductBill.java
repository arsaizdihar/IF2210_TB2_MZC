package mzc.app.model;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductBill")
@Getter @Setter @NoArgsConstructor
public class ProductBill extends BaseModel {
    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false)
    private long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private transient Product product;

    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false)
    private long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId", referencedColumnName = "id")
    private transient Bill bill;

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
