import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Customer implements PaymentMethod {
    private String customerId;
    private String customerName;
    private List<Product> purchasedProducts; // Danh sách lưu trữ sản phẩm đã mua

    @Override
    public void processPayment(double amount) {
        System.out.println("thanh toan?");
    }

    // Constructor
    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.purchasedProducts = new ArrayList<>(); // Khởi tạo danh sách
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Phương thức để đặt hàng và thanh toán sản phẩm
    public void orderProduct(StoreManager storeManager, String productId) {
        Product product = storeManager.getProductById(productId); // Lấy sản phẩm từ StoreManager
        if (product != null && product.getQuantity() > 0) {
            // Giảm số lượng sản phẩm
            product.setQuantity(product.getQuantity() - 1);
            purchasedProducts.add(product); // Thêm sản phẩm vào danh sách đã mua
            System.out.println("Đã đặt hàng thành công: " + product.getProductName());
            generateInvoice(product); // Tạo hóa đơn
            storeManager.saveProducts(); // Lưu thay đổi vào file
            saveCustomerData(); // Lưu thông tin khách hàng vào file
        } else {
            System.out.println("Sản phẩm không có sẵn hoặc đã hết hàng.");
        }
    }

    // Phương thức để tạo hóa đơn
    private void generateInvoice(Product product) {
        System.out.println("---- Hóa đơn ----");
        System.out.println("Tên sản phẩm: " + product.getProductName());
        System.out.println("Giá: " + product.getPrice());
        System.out.println("-----------------");
    }

    // Lưu thông tin khách hàng và sản phẩm đã mua vào file
    private void saveCustomerData() {
        String filename = "customers.txt";
        List<String> fileData = new ArrayList<>();

        // Đọc file hiện tại để lấy thông tin tất cả khách hàng
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileData.add(line);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }

        // Xóa thông tin cũ của khách hàng hiện tại và thêm lại với sản phẩm mới
        fileData.removeIf(line -> line.startsWith(customerId)); // Xóa dòng khách hàng cũ

        // Thêm dòng mới với tất cả sản phẩm đã mua
        StringBuilder customerData = new StringBuilder();
        customerData.append(customerId).append(",").append(customerName);
        for (Product product : purchasedProducts) {
            customerData.append(",").append(product.getClass().getSimpleName()).append(",")
                    .append(product.getProductId()).append(",").append(product.getProductName()).append(",")
                    .append(product.getPrice());
        }

        // Thêm dữ liệu khách hàng mới vào danh sách
        fileData.add(customerData.toString());

        // Ghi toàn bộ dữ liệu vào file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : fileData) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Đã lưu thông tin khách hàng và sản phẩm đã mua vào file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // Phương thức đọc lại dữ liệu từ file
    public static void loadCustomersFromFile(List<Customer> customers) {
        String filename = "customers.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) { // Kiểm tra nếu dòng dữ liệu có ít nhất 3 phần tử (ID, tên khách hàng, sản phẩm)
                    String customerId = data[0];
                    String customerName = data[1];
                    Customer customer = new Customer(customerId, customerName);

                    for (int i = 2; i < data.length; i += 4) {
                        String productType = data[i];
                        String productId = data[i + 1];
                        String productName = data[i + 2];
                        double price = Double.parseDouble(data[i + 3]);

                        Product product = createProductFromData(
                                new String[] { productType, productId, productName, String.valueOf(price) });
                        if (product != null) {
                            customer.getPurchasedProducts().add(product);
                        }
                    }
                    customers.add(customer); // Thêm khách hàng vào danh sách
                }
            }
            System.out.println("Đã tải danh sách khách hàng từ file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    private static Product createProductFromData(String[] data) {
        String productType = data[0];
        String productId = data[1];
        String productName = data[2];
        double price = Double.parseDouble(data[3]);

        if (productType.equalsIgnoreCase("Laptop")) {
            // Khởi tạo Laptop
            return new Laptop(productId, productName, price, 1, "Intel", 8, 256); // Giá trị mẫu cho CPU, RAM, Storage
        } else if (productType.equalsIgnoreCase("DienThoai")) {
            // Khởi tạo Điện Thoại
            return new DienThoai(productId, productName, price, 1, "6.5", 4000); // Giá trị mẫu cho màn hình, pin
        }
        return null;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Product> getPurchasedProducts() {
        return purchasedProducts;
    }

    // Phương thức để xem các sản phẩm đã mua
    public void viewPurchasedProducts() {
        System.out.println("Danh sách sản phẩm đã mua:");
        if (purchasedProducts.isEmpty()) {
            System.out.println("Chưa có sản phẩm nào được mua.");
        } else {
            for (Product product : purchasedProducts) {
                product.displayProduct(); // Hiển thị thông tin sản phẩm
            }
        }
    }
}
