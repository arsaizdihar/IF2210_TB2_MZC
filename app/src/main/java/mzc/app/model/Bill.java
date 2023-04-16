package mzc.app.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Bill")
@Getter @Setter @NoArgsConstructor
public class Bill extends BaseModel {
    @Transient
    private transient boolean productsLoaded = false;

    @OneToMany(mappedBy = "bill")
    private transient List<ProductBill> products = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false)
    private long customerId;

    @Column boolean isFixed = false;

    @ManyToOne()
    @JoinColumn(name = "customerId", nullable = false)
    private transient Customer customer;

    public Bill(Customer customer) {
        setCustomer(customer);
    }

    public boolean equals(Bill b) {
        return this.id == b.id && this.isFixed == b.isFixed && this.customerId == b.customerId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
