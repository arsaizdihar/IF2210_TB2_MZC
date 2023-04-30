package mzc.app.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "bill")
@Table(name = "bill")
@Getter @Setter @NoArgsConstructor
public class Bill extends BaseModel {
    @Transient
    private transient boolean productsLoaded = false;

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private transient List<ProductBill> products = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false, name = "customerId")
    private long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false, referencedColumnName = "id")
    private transient Customer customer;

    public Bill(Customer customer) {
        setCustomer(customer);
    }

    public boolean equals(Bill b) {
        return this.id == b.id && this.customerId == b.customerId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
