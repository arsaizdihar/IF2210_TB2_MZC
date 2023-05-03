package mzc.app.model;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;
import mzc.app.utils.FileManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "product")
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseModel {
    @EqualCheck
    @Column
    private int stock = 0;

    @EqualCheck
    @Column
    private String name;

    @EqualCheck
    @Column
    private BigDecimal price;

    @EqualCheck
    @Column
    private BigDecimal buyPrice;

    @EqualCheck
    @Column
    private String category;

    @EqualCheck
    @Column
    @Setter(AccessLevel.PROTECTED)
    private String imagePath;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private transient List<ProductBill> bills = new ArrayList<>();

    @Transient
    @Setter(AccessLevel.NONE)
    private transient Image image;

    public Product(int stock, String name, BigDecimal price, BigDecimal buyPrice, String category, String imagePath) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Image getImage() {
        if (image == null) {
            image = new Image("file:" + imagePath);
        }
        return image;
    }

    public void updateImage(String realPath) throws IOException {
        String dstPath = FileManager.getDataStorePath(realPath);
        FileManager.copyFile(realPath, dstPath);
        imagePath = dstPath;
        image = null;
    }

    public static List<Product> getSeed(String path) {
        List<Product> products = new ArrayList<>();
        products.add(new Product(10, "Kopi Susu Si Eko", new BigDecimal("24000"), new BigDecimal("12000"), "Minuman", ""));
        products.add(new Product(0, "Americano", new BigDecimal("20000"), new BigDecimal("8000"), "Minuman", ""));
        products.add(new Product(5, "Teh Earl Grey", new BigDecimal("20000"), new BigDecimal("8000"), "Minuman", ""));
        products.add(new Product(10, "Chicken Katsu Mushroom Butter Chicken", new BigDecimal("24000"), new BigDecimal("16000"), "Makanan", ""));
        products.add(new Product(0, "Chicken Katsu Curry Rice", new BigDecimal("28000"), new BigDecimal("20000"), "Makanan", ""));
        products.add(new Product(5, "Nasi Goreng Bawang Merah", new BigDecimal("25000"), new BigDecimal("12000"), "Makanan", ""));

        products.forEach(product -> {
            try {
                product.updateImage(path);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        return products;
    }
}
