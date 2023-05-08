package mzc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "bill")
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
public class Bill extends BaseModel {
    @Transient
    @JsonIgnore
    private transient boolean productsLoaded = false;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonIgnore
    private transient Set<ProductBill> products = new LinkedHashSet<>();

    @Setter(AccessLevel.NONE)
    @Column(name = "customerId")
    private long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", nullable = false, referencedColumnName = "id")
    @JsonIgnore
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
