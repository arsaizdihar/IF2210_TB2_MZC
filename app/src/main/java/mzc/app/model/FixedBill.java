package mzc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
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
    @JsonIgnore
    private transient Set<ProductHistory> products = new LinkedHashSet<>();

    @EqualCheck
    @Setter(AccessLevel.NONE)
    @Column(name = "customerId")
    private long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private transient Customer customer;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    public FixedBill(Customer customer) {
        setCustomer(customer);
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    public FixedBill(Customer customer, Timestamp timestamp) {
        setCustomer(customer);
        createdAt = timestamp;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
