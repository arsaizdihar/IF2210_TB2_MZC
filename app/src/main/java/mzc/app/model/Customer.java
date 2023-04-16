package mzc.app.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "Customer")
@Getter @Setter @NoArgsConstructor
public class Customer extends BaseModel {
    @Column
    private String name = "";

    @Column
    private String phone = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private transient List<Bill> bills = new ArrayList<>();

    @Column
    private CustomerType type = CustomerType.BASIC;

    @Column
    private boolean isDeactivated = false;

    @Column
    private int points = 0;

    @Transient
    private transient boolean billsLoaded = false;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Customer(String name, String phone, CustomerType type) {
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public boolean equals(Customer c) {
        return this.id == c.id && this.name.equals(c.name) && this.phone.equals(c.phone) && this.type.equals(c.type) && this.points == c.points;
    }
}
