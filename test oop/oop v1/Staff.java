
// Staff.java
import java.util.List;

public class Staff extends Person {

    // Constructor của Staff2

    public Staff(String name, String id) {
        super(name, id);
    }

    // Cài đặt phương thức trừu tượng
    @Override
    public void viewProducts(List<Product> products) {
        System.out.println("Danh sách sản phẩm (Staff):");
        for (Product product : products) {
            product.displayProduct();
            // chỉ xem tt sp không cần hiện tt nhập xuất , đã bán
        }
    }
}
