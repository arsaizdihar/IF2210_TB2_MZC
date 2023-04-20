package mzc.app.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FixedBill")
@Getter
@Setter @NoArgsConstructor
public class FixedBill extends BaseModel {
    @Transient
    private transient boolean productsLoaded = false;

    @OneToMany(mappedBy = "bill")
    private transient List<ProductHistory> products = new ArrayList<>();

    @EqualCheck
    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false)
    private long customerId;

    @ManyToOne()
    @JoinColumn(name = "customerId", nullable = false)
    private transient Customer customer;

    public FixedBill(Customer customer) {
        setCustomer(customer);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
