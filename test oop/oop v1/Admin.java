import java.util.List;

public class Admin extends Person {
    private CustomerManager customerManager;
    private StoreManager storeManager; // Giả sử bạn cũng có một StoreManager để quản lý sản phẩm

    // Constructor của Admin
    public Admin(String name, String id, CustomerManager customerManager, StoreManager storeManager) {
        super(name, id);
        this.customerManager = customerManager;
        this.storeManager = storeManager;
    }

    // Cài đặt phương thức trừu tượng để xem sản phẩm
    @Override
    public void viewProducts(List<Product> products) {
        System.out.println("Danh sách sản phẩm:");
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
            return;
        }
        for (Product product : products) {
            product.displayProduct();
            System.out.println("Số lượng đã bán: " + product.getSoldQuantity());
            System.out.println("Số lượng còn lại: " + product.getQuantity());
            System.out.println(); // Thêm dòng trống để dễ nhìn
        }
    }

    // Phương thức thêm sản phẩm
    public void addProduct(List<Product> products, Product product) {
        products.add(product);
        storeManager.saveProducts(); // Lưu danh sách sản phẩm vào file
        System.out.println("Đã thêm sản phẩm thành công: " + product.getProductName());
    }

    // Phương thức sửa thông tin sản phẩm
    public void editProduct(List<Product> products, String productId, String newName, double newPrice,
            int newQuantity) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                product.setProductName(newName);
                product.setPrice(newPrice);
                product.setQuantity(newQuantity);
                storeManager.saveProducts(); // Lưu thay đổi vào file
                System.out.println("Cập nhật sản phẩm thành công: " + product.getProductName());
                return;
            }
        }
        System.out.println("Không tìm thấy sản phẩm với ID: " + productId);
    }

    // Phương thức xóa sản phẩm
    public void deleteProduct(List<Product> products, String productId) {
        if (products.removeIf(product -> product.getProductId().equals(productId))) {
            storeManager.saveProducts(); // Lưu thay đổi vào file
            System.out.println("Xóa sản phẩm thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm để xóa với ID: " + productId);
        }
    }

    // Phương thức thêm khách hàng
    public void addCustomer(Customer customer) {
        customerManager.addCustomer(customer); // Gọi phương thức thêm khách hàng trong CustomerManager
    }

    // Phương thức sửa thông tin khách hàng
    public void editCustomer(String customerId, String newName) {
        customerManager.editCustomer(customerId, newName); // Gọi phương thức sửa trong CustomerManager
    }

    // Phương thức xóa khách hàng
    public void deleteCustomer(String customerId) {
        customerManager.deleteCustomer(customerId); // Gọi phương thức xóa trong CustomerManager
    }

    // Phương thức xem tất cả khách hàng
    public void viewCustomers() {
        customerManager.viewCustomers(); // Gọi phương thức xem trong CustomerManager
    }
}
