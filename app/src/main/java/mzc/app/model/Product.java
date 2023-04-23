package mzc.app.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "product")
@Table(name = "product")
@Getter @Setter @NoArgsConstructor
public class Product extends BaseModel {
    @EqualCheck
    @Column
    private int stock = 0;

    @EqualCheck
    @Column
    private String name;

    @EqualCheck
    @Column
    private int price;

    @EqualCheck
    @Column
    private int buyPrice;

    @EqualCheck
    @Column
    private String category;

    @EqualCheck
    @Column
    private String image;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private transient List<ProductBill> bills = new ArrayList<>();

    public Product(int stock, String name, int price, int buyPrice, String category, String image) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.image = image;
    }
}
