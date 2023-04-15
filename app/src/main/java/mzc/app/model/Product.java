package mzc.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@Getter @Setter @NoArgsConstructor
public class Product extends BaseModel {
    @Column
    private int stock;

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

    public Product(int stock, String name, int price, int buyPrice, String category, String image) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.image = image;
    }
}
