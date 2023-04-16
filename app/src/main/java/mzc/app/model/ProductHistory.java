package mzc.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ProductHistory")
@Getter @Setter @NoArgsConstructor
public class ProductHistory extends BaseModel {
    @Column
    private String name;

    @Column
    private int price;

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
