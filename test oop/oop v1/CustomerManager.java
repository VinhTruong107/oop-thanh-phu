import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerManager {
    private List<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
        loadCustomersFromFile(); // Tải danh sách khách hàng từ file
    }

    // Thêm khách hàng mới
    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomersToFile(); // Lưu danh sách khách hàng vào file
        System.out.println("Đã thêm khách hàng: " + customer.getCustomerName());
    }

    // Sửa thông tin khách hàng
    public void editCustomer(String customerId, String newName) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            customer.setCustomerName(newName); // Cập nhật tên khách hàng
            saveCustomersToFile(); // Lưu thay đổi vào file
            System.out.println("Đã sửa tên khách hàng thành: " + newName);
        } else {
            System.out.println("Không tìm thấy khách hàng với ID: " + customerId);
        }
    }

    // Xóa khách hàng
    public void deleteCustomer(String customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            System.out.print("Bạn có chắc chắn muốn xóa khách hàng " + customer.getCustomerName() + " không? (y/n): ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String confirmation = reader.readLine();
                if (confirmation.equalsIgnoreCase("y")) {
                    customers.remove(customer);
                    saveCustomersToFile(); // Lưu thay đổi vào file
                    System.out.println("Đã xóa khách hàng.");
                } else {
                    System.out.println("Đã hủy thao tác xóa.");
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi đọc xác nhận: " + e.getMessage());
            }
        } else {
            System.out.println("Không tìm thấy khách hàng để xóa.");
        }
    }

    // Xem tất cả khách hàng
    public void viewCustomers() {
        System.out.println("Danh sách khách hàng:");
        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào.");
        } else {
            // Sử dụng Set để theo dõi khách hàng đã in ra
            Set<String> printedCustomers = new HashSet<>();
            for (Customer customer : customers) {
                if (!printedCustomers.contains(customer.getCustomerId())) {
                    printedCustomers.add(customer.getCustomerId());
                    System.out.println("ID: " + customer.getCustomerId() + ", Tên: " + customer.getCustomerName());

                    // Kiểm tra nếu khách hàng có sản phẩm đã mua
                    if (customer.getPurchasedProducts().isEmpty()) {
                        System.out.println("Chưa có sản phẩm nào được mua.");
                    } else {
                        customer.viewPurchasedProducts(); // Hiển thị các sản phẩm đã mua
                    }
                    System.out.println(); // Thêm dòng trống để dễ nhìn
                }
            }
        }
    }

    // Lấy khách hàng theo ID
    public Customer getCustomerById(String customerId) {
        return customers.stream()
                .filter(customer -> customer.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    // Lưu danh sách khách hàng vào file
    public void saveCustomersToFile() {
        String filename = "customers.txt"; // Tên file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Customer customer : customers) {
                writer.write(customer.getCustomerId() + "," + customer.getCustomerName());
                for (Product product : customer.getPurchasedProducts()) {
                    writer.write("," + product.getClass().getSimpleName() + "," + product.getProductId() + ","
                            + product.getProductName() + "," + product.getPrice());
                }
                writer.newLine();
            }
            System.out.println("Đã lưu danh sách khách hàng vào file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // Đọc danh sách khách hàng từ file
    public void loadCustomersFromFile() {
        String filename = "customers.txt"; // Tên file
        File file = new File(filename);

        // Kiểm tra xem file có tồn tại không
        if (!file.exists()) {
            System.out.println("File " + filename + " không tồn tại. Đang tạo file mới...");
            try {
                file.createNewFile(); // Tạo file mới
            } catch (IOException e) {
                System.out.println("Lỗi khi tạo file: " + e.getMessage());
                return; // Thoát nếu không tạo được file
            }
        }

        // Đọc khách hàng từ file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Set<String> customerIds = new HashSet<>(); // Set để theo dõi ID khách hàng

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) { // Kiểm tra định dạng dữ liệu
                    String customerId = data[0];

                    // Kiểm tra nếu khách hàng đã tồn tại
                    if (customerIds.contains(customerId)) {
                        continue; // Bỏ qua nếu đã tồn tại
                    }

                    customerIds.add(customerId); // Thêm ID vào Set

                    String customerName = data[1];
                    Customer customer = new Customer(customerId, customerName);

                    for (int i = 2; i < data.length; i += 4) {
                        String productType = data[i]; // Loại sản phẩm
                        String productId = data[i + 1]; // ID sản phẩm
                        String productName = data[i + 2]; // Tên sản phẩm
                        double price = Double.parseDouble(data[i + 3]); // Giá sản phẩm

                        // Tạo sản phẩm tương ứng
                        Product product = null;
                        if (productType.equalsIgnoreCase("Laptop")) {
                            product = new Laptop(productId, productName, price, 1, "CPU1", 8, 256); // Thay thông tin
                        } else if (productType.equalsIgnoreCase("DienThoai")) {
                            product = new DienThoai(productId, productName, price, 1, "6 inch", 4000); // Thay thông tin
                        }

                        if (product != null) {
                            customer.getPurchasedProducts().add(product); // Thêm sản phẩm vào danh sách đã mua
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
}
