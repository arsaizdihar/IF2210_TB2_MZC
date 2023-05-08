package mzc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.*;
import java.util.function.Consumer;

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
    @JsonIgnore
    private transient Set<ProductBill> bills = new LinkedHashSet<>();

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

    public Product(int stock, String name, BigDecimal price, BigDecimal buyPrice, String category) {
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
        this.category = category;
        this.imagePath = "";
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

    @JsonIgnore
    public IPrice getPriceView() {
        return PriceFactory.createPriceView(this.price);
    }

    @JsonIgnore
    public IPrice getBuyPriceView() {
        return PriceFactory.createPriceView(this.buyPrice);
    }

    @JsonIgnore
    public Image getImage() {
        return FileManager.getImage(this.imagePath);
    }

    @JsonIgnore
    public void getImageAsync(Consumer<Image> callback) {
        FileManager.getImageAsync(this.imagePath, callback);
    }

    public void updateImage(String realPath) throws IOException {
        String dstPath = FileManager.getRandomizedDataStorePath(realPath);
        FileManager.copyFile(realPath, dstPath);
        imagePath = dstPath;
    }

    @JsonIgnore
    public static List<Product> getSeed() {
        List<String> images = new ArrayList<>(List.of(new String[]{"coffee.png", "tea.png", "katsu.png", "katsu.png", "fried-rice.png"}));
        List<Product> products = new ArrayList<>();
        products.add(new Product(10, "Kopi Susu Si Eko", new BigDecimal("24000"), new BigDecimal("12000"), "Minuman"));
        products.add(new Product(5, "Teh Earl Grey", new BigDecimal("20000"), new BigDecimal("8000"), "Minuman"));
        products.add(new Product(10, "Chicken Katsu Mushroom Butter Chicken", new BigDecimal("24000"), new BigDecimal("16000"), "Makanan"));
        products.add(new Product(0, "Chicken Katsu Curry Rice", new BigDecimal("28000"), new BigDecimal("20000"), "Makanan"));
        products.add(new Product(5, "Nasi Goreng Bawang Merah", new BigDecimal("25000"), new BigDecimal("12000"), "Makanan"));

        for (int i = 0; i < products.size(); i++) {
            try {
                products.get(i).updateImage(Objects.requireNonNull(FileManager.getResourcePath("/mzc/app/assets/" + images.get(i))).substring(6));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return products;
    }
}
