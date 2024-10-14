import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StoreManager storeManager = new StoreManager();
        CustomerManager customerManager = new CustomerManager(); // Tạo CustomerManager để quản lý khách hàng
        Scanner scanner = new Scanner(System.in);

        // Tải dữ liệu sản phẩm và khách hàng từ file
        storeManager.loadProductsFromFile();
        customerManager.loadCustomersFromFile();

        while (true) {
            System.out.println("===== Hệ thống quản lý cửa hàng thiết bị điện tử =====");
            System.out.println("1. Đăng nhập với tư cách Admin");
            System.out.println("2. Đăng nhập với tư cách Nhân viên");
            System.out.println("3. Đăng nhập với tư cách Khách hàng");
            System.out.println("4. Thoát hệ thống");
            System.out.print("Chọn tùy chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    adminMenu(storeManager, customerManager, scanner); // Cập nhật tham số để thêm customerManager
                    break;
                case 2:
                    staffMenu(storeManager, scanner);
                    break;
                case 3:
                    customerLoginMenu(customerManager, storeManager, scanner);
                    break;
                case 4:
                    System.out.println("Đã thoát hệ thống.");
                    return; // Thoát chương trình
                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    // Các phương thức khác giữ nguyên hoặc sửa đổi nếu cần thiết
    private static void adminMenu(StoreManager storeManager, CustomerManager customerManager, Scanner scanner) {
        while (true) {
            System.out.println("===== Menu Admin =====");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    productManagementMenu(storeManager, scanner); // Gọi menu quản lý sản phẩm
                    break;
                case 2:
                    customerManagementMenu(customerManager, scanner); // Gọi menu quản lý khách hàng
                    break;
                case 3:
                    return; // Quay lại menu chính
                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void productManagementMenu(StoreManager storeManager, Scanner scanner) {
        while (true) {
            System.out.println("===== Quản lý sản phẩm =====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Sửa sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Xem danh sách sản phẩm");
            System.out.println("5. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    // Thêm sản phẩm
                    System.out.print("Nhập loại sản phẩm (Laptop/DienThoai): ");
                    String type = scanner.nextLine();
                    System.out.print("Nhập ID sản phẩm: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập tên sản phẩm: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập giá sản phẩm: ");
                    double price = scanner.nextDouble();
                    System.out.print("Nhập số lượng sản phẩm: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Đọc bỏ dòng trống

                    if (type.equalsIgnoreCase("Laptop")) {
                        System.out.print("Nhập CPU: ");
                        String cpu = scanner.nextLine();
                        System.out.print("Nhập RAM (GB): ");
                        int ram = scanner.nextInt();
                        System.out.print("Nhập bộ nhớ (GB): ");
                        int storage = scanner.nextInt();
                        Laptop laptop = new Laptop(id, name, price, quantity, cpu, ram, storage);
                        storeManager.addProduct(laptop);
                    } else if (type.equalsIgnoreCase("DienThoai")) {
                        System.out.print("Nhập kích thước màn hình: ");
                        String screenSize = scanner.nextLine();
                        System.out.print("Nhập dung lượng pin (mAh): ");
                        int batteryCapacity = scanner.nextInt();
                        DienThoai phone = new DienThoai(id, name, price, quantity, screenSize, batteryCapacity);
                        storeManager.addProduct(phone);
                    } else {
                        System.out.println("Loại sản phẩm không hợp lệ!");
                    }
                    break;

                case 2:
                    // Sửa sản phẩm
                    System.out.print("Nhập ID sản phẩm cần sửa: ");
                    String editId = scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhập giá mới: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Nhập số lượng mới: ");
                    int newQuantity = scanner.nextInt();
                    storeManager.editProduct(editId, newName, newPrice, newQuantity);
                    break;

                case 3:
                    // Xóa sản phẩm
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String deleteId = scanner.nextLine();
                    storeManager.deleteProduct(deleteId);
                    break;

                case 4:
                    // Xem danh sách sản phẩm
                    storeManager.viewProducts();
                    break;

                case 5:
                    return; // Quay lại menu Admin

                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void customerManagementMenu(CustomerManager customerManager, Scanner scanner) {
        while (true) {
            System.out.println("===== Quản lý khách hàng =====");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Sửa thông tin khách hàng");
            System.out.println("3. Xóa khách hàng");
            System.out.println("4. Xem danh sách khách hàng");
            System.out.println("5. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    // Thêm khách hàng
                    System.out.print("Nhập ID khách hàng mới: ");
                    String newCustomerId = scanner.nextLine();
                    System.out.print("Nhập tên khách hàng: ");
                    String newCustomerName = scanner.nextLine();
                    Customer newCustomer = new Customer(newCustomerId, newCustomerName);
                    customerManager.addCustomer(newCustomer);
                    break;

                case 2:
                    // Sửa thông tin khách hàng
                    System.out.print("Nhập ID khách hàng cần sửa: ");
                    String customerIdToEdit = scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String updatedName = scanner.nextLine();
                    customerManager.editCustomer(customerIdToEdit, updatedName);
                    break;

                case 3:
                    // Xóa khách hàng
                    System.out.print("Nhập ID khách hàng cần xóa: ");
                    String customerIdToDelete = scanner.nextLine();
                    customerManager.deleteCustomer(customerIdToDelete);
                    break;

                case 4:
                    // Xem danh sách khách hàng
                    customerManager.viewCustomers();
                    break;

                case 5:
                    return; // Quay lại menu Admin

                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void staffMenu(StoreManager storeManager, Scanner scanner) {
        while (true) {
            System.out.println("===== Menu Nhân viên =====");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    storeManager.viewProducts();
                    break;
                case 2:
                    return; // Quay lại menu chính
                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void customerLoginMenu(CustomerManager customerManager, StoreManager storeManager, Scanner scanner) {
        while (true) {
            System.out.println("===== Menu Đăng nhập Khách hàng =====");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("3. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    // Đăng nhập
                    System.out.print("Nhập ID khách hàng: ");
                    String customerId = scanner.nextLine();
                    Customer customer = customerManager.getCustomerById(customerId);
                    if (customer != null) {
                        customerMenu(storeManager, scanner, customer);
                    } else {
                        System.out.println("Không tìm thấy khách hàng với ID này.");
                    }
                    break;

                case 2:
                    // Đăng ký
                    System.out.print("Nhập ID khách hàng mới: ");
                    String newCustomerId = scanner.nextLine();
                    System.out.print("Nhập tên khách hàng: ");
                    String newCustomerName = scanner.nextLine();
                    Customer newCustomer = new Customer(newCustomerId, newCustomerName);
                    customerManager.addCustomer(newCustomer);
                    System.out.println("Đã đăng ký thành công. Bạn có thể đăng nhập ngay bây giờ.");
                    break;

                case 3:
                    return; // Quay lại menu chính
                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void customerMenu(StoreManager storeManager, Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("===== Menu Khách hàng =====");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Đặt hàng sản phẩm");
            System.out.println("3. Xem lại sản phẩm đã mua");
            System.out.println("4. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            switch (choice) {
                case 1:
                    storeManager.viewProducts();
                    break;
                case 2:
                    System.out.print("Nhập ID sản phẩm để đặt hàng: ");
                    String productId = scanner.nextLine();
                    customer.orderProduct(storeManager, productId); // Sửa lại để gọi phương thức đúng
                    break;
                case 3:
                    customer.viewPurchasedProducts();
                    break;
                case 4:
                    return; // Quay lại menu chính
                default:
                    System.out.println("Tùy chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }
}
