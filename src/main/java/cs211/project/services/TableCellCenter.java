package cs211.project.services;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class TableCellCenter<S,T> extends TableCell<S,T> {

    public <T> TableCell<T, String> CellAsString(TableColumn<T, String> cell) {
        return new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }

                setAlignment(Pos.CENTER); // กำหนดให้ข้อมูลแสดงตรงกลาง
            }
        };
    }
    public <T> TableCell<T, ImageView> CellAsImageView(TableColumn<T, ImageView> cell) {
        return new TableCell<T, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setGraphic(item);
                }

                setAlignment(Pos.CENTER); // กำหนดให้ข้อมูลแสดงตรงกลาง
            }
        };
    }

    public <T> TableCell<T, Boolean> CellAsBoolean(TableColumn<T, Boolean> cell) {
        return new TableCell<T, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Open" : "Closed");

                }

                setAlignment(Pos.CENTER); // กำหนดให้ข้อมูลแสดงตรงกลาง
            }
        };
    }


}
