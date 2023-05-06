package mzc.app.modules.report;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mzc.app.model.ProductHistory;

import java.util.List;
import java.util.Set;

public class ProductHistoryTableUtil {
    public static ObservableList<ProductHistory> createTable(Set<ProductHistory> productHistories) {
        return FXCollections.<ProductHistory>observableArrayList(productHistories);
    }
    // Return Image Table Column
    public static TableColumn<ProductHistory, Image> getImageColumn() {
        TableColumn<ProductHistory, Image> imageColumn = new TableColumn<>("Gambar");
        imageColumn.setCellFactory(col -> {
            TableCell<ProductHistory, Image> cell =
                    new TableCell<ProductHistory, Image>() {
                        @Override
                        public void updateItem(Image item, boolean empty) {
                            super.updateItem(item, empty);

                            this.setText(null);
                            this.setGraphic(null);
                            if (!empty) {
                                ProductHistory productHistory = this.getTableView().getItems().get(this.getIndex());
                                ImageView imageView = new ImageView(productHistory.getImageView());
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                                this.setGraphic(imageView);
                            }
                        }
                    };
            return cell;
        });
        return imageColumn;
    }


    public static TableColumn<ProductHistory, String> getProductNameColumn() {
        TableColumn<ProductHistory, String> productNameColumn = new TableColumn<>("Nama Barang");
        productNameColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()).getReadOnlyProperty()
        );
        return productNameColumn;
    }

    // Return category table column
    public static TableColumn<ProductHistory, String> getCategoryColumn() {
        TableColumn<ProductHistory, String> categoryColumn = new TableColumn<>("Kategori");
        categoryColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCategory()).getReadOnlyProperty()
        );
        return categoryColumn;
    }

    public static TableColumn<ProductHistory, String> getBuyPriceColumn() {
        TableColumn<ProductHistory, String> buyPriceColumn = new TableColumn<>("Harga Beli");
        buyPriceColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getBuyPriceView().toString()).getReadOnlyProperty()
        );
        return buyPriceColumn;
    }

    public static TableColumn<ProductHistory, String> getPriceColumn() {
        TableColumn<ProductHistory, String> priceColumn = new TableColumn<>("Harga Jual");
        priceColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPriceView().toString()).getReadOnlyProperty()
        );
        return priceColumn;
    }

    public static TableColumn<ProductHistory, String> getAmountColumn() {
        TableColumn<ProductHistory, String> quantityColumn = new TableColumn<>("Jumlah");
        quantityColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAmount().toString()).getReadOnlyProperty()
        );
        return quantityColumn;
    }



//    public static TableColumn<ProductHistory, Image> getImageColumn() {
//        TableColumn<ProductHistory, Image> imageColumn = new TableColumn<>("Image");
//        imageColumn.setCellValueFactory(col -> {
//            TableCell<ProductHistory, Image> cell =
//            new TableCell<ProductHistory, Image>() {
//                @Override
//                protected void updateItem(Image item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    this.setText(null);
//                    this.setGraphic(null);
//
//                    if (!empty) {
//                        var productHistory = col.getTableView().getItems().get(this.getIndex());
//                        var imageView = new ImageView(productHistory.getImageView());
//                        this.setGraphic(imageView);
//                    }
//                }
//            };
//            return cell.itemProperty();
//        });
//        return imageColumn;
//    }
}
