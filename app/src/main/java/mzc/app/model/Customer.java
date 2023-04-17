package mzc.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "Customer")
@Getter @Setter @NoArgsConstructor
public class Customer extends BaseModel {
    @EqualCheck
    @Column
    private String name = "";

    @EqualCheck
    @Column
    private String phone = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private transient List<Bill> bills = new ArrayList<>();

    @EqualCheck
    @Column
    private CustomerType type = CustomerType.BASIC;

    @EqualCheck
    @Column
    private boolean isDeactivated = false;

    @EqualCheck
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
}
