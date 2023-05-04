package mzc.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "customer")
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseModel {
    @EqualCheck
    @Column
    private String name = "";

    @EqualCheck
    @Column
    private String phone = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private transient List<Bill> bills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private transient List<FixedBill> fixedBills = new ArrayList<>();

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

    @Override
    public String toString() {
        return this.name;
    }

    public static List<Customer> getSeeder() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Arsa Izdihar Islam", "089512341234", CustomerType.MEMBER));
        customers.add(new Customer("Akbar Maulana Ridho", "089512341235", CustomerType.MEMBER));
        customers.add(new Customer("Fakhri Muhammad Mahendra", "089512341236", CustomerType.MEMBER));
        customers.add(new Customer("Razzan Yoni", "0895123412347", CustomerType.VIP));
        customers.add(new Customer("Kenneth Ezekiel Suprantoni", "089512341234", CustomerType.VIP));
        return customers;
    }
}
