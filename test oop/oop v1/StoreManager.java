import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StoreManager {
    private List<Product> products;

    public StoreManager() {
        products = new ArrayList<>();
        loadProductsFromFile();
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
    }

    public void editProduct(String productId, String newName, double newPrice, int newQuantity) {
        Product product = getProductById(productId);
        if (product != null) {
            product.setProductName(newName);
            product.setPrice(newPrice);
            product.setQuantity(newQuantity);
            saveProducts();
        } else {
            System.out.println("Sản phẩm không tồn tại.");
        }
    }

    public void deleteProduct(String productId) {
        Product productToRemove = getProductById(productId);
        if (productToRemove != null) {
            products.remove(productToRemove);
            saveProducts(); // Lưu danh sách sản phẩm vào file sau khi xóa
            System.out.println("Sản phẩm với ID " + productId + " đã được xóa thành công.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID " + productId + ".");
        }
    }

    public void viewProducts() {
        System.out.println("Danh sách sản phẩm:");
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            for (Product product : products) {
                product.displayProduct();
            }
        }
    }

    public Product getProductById(String productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void saveProducts() {
        try {
            Product.saveProductsToFile(products, "products.txt");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public void loadProductsFromFile() {
        String filename = "products.txt"; // Đặt tên file cố định
        File file = new File(filename);

        // Kiểm tra xem file có tồn tại không
        if (!file.exists()) {
            System.out.println("File " + filename + " không tồn tại. Đang tạo file mới...");
            try {
                file.createNewFile(); // Tạo file mới
                // Thêm một số sản phẩm mặc định vào file mới
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("LAP001,Laptop A,1000.0,10,Intel i5,8,256");
                    writer.newLine();
                    writer.write("LAP002,Laptop B,1200.0,5,Intel i7,16,512");
                    writer.newLine();
                    writer.write("DT001,DienThoai A,300.0,15,6.1,4000");
                    writer.newLine();
                    writer.write("DT002,DienThoai B,450.0,8,6.5,5000");
                    writer.newLine();
                }
                System.out.println("File mới đã được tạo và sản phẩm đã được thêm.");
            } catch (IOException e) {
                System.out.println("Lỗi khi tạo file: " + e.getMessage());
                return; // Thoát khỏi phương thức nếu không tạo được file
            }
        }

        // Đọc sản phẩm từ file
        try {
            products = Product.loadProductsFromFile(filename);
            System.out.println("Đã đọc sản phẩm từ file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}
