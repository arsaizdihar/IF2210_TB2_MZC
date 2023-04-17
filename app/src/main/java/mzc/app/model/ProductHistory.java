package mzc.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;

import javax.persistence.*;

@Entity
@Table(name = "ProductHistory")
@Getter @Setter @NoArgsConstructor
public class ProductHistory extends BaseModel {
    @EqualCheck
    @Column
    private String name;

    @EqualCheck
    @Column
    private int price;

    @EqualCheck
    @Column
    private int buyPrice;

    @Column
    private String category;

    @Column
    private String image;

    public ProductHistory(String name, int price, int buyPrice, String category, String image) {
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.image = image;
    }
}
