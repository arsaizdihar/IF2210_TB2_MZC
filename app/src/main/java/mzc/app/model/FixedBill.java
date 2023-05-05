package mzc.app.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "fixedbill")
@Table(name = "fixedbill")
@Getter
@Setter
@NoArgsConstructor
public class FixedBill extends BaseModel {
    @Transient
    private transient boolean productsLoaded = false;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private transient Set<ProductHistory> products = new LinkedHashSet<>();

    @EqualCheck
    @Setter(AccessLevel.NONE)
    @Column(insertable = false, updatable = false, name = "customerId")
    private long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false, referencedColumnName = "id")
    private transient Customer customer;

    public FixedBill(Customer customer) {
        setCustomer(customer);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
