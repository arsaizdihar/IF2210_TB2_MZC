package mzc.app.model;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.price.IPrice;
import mzc.app.utils.FileManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "product")
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseModel implements ISoftDelete {
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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private transient Set<ProductBill> bills = new LinkedHashSet<>();

    @Transient
    @Setter(AccessLevel.NONE)
    private transient Image image;

    @EqualCheck
    @Column
    private Boolean deleted = false;

    public Product(int stock, String name, BigDecimal price, BigDecimal buyPrice, String category, String imagePath) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.imagePath = imagePath;
        this.deleted = false;
    }

    public Product(int stock, String name, BigDecimal price, BigDecimal buyPrice, String category, String imagePath, Boolean deleted) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.imagePath = imagePath;
        this.deleted = deleted;
    }

    public IPrice getPriceView() {
        return PriceFactory.createPriceView(this.price);
    }

    public IPrice getBuyPriceView() {
        return PriceFactory.createPriceView(this.buyPrice);
    }

    public Image getImage() {
        if (image == null) {
            image = new Image("file:" + imagePath);
        }
        return image;
    }

    public void updateImage(String realPath) throws IOException {
        String dstPath = FileManager.getRandomizedDataStorePath(realPath);
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
