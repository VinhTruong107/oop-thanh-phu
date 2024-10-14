import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Product implements Serializable {
    protected String productId;
    protected String productName;
    protected double price;
    protected int quantity;
    protected int soldQuantity; // Số lượng đã bán

    // Constructor
    public Product(String productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.soldQuantity = 0; // Khởi tạo số lượng đã bán là 0
    }

    // Hiển thị thông tin sản phẩm (abstract method)
    public abstract void displayProduct();

    // Getters và setters cho các thuộc tính
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity; // Getter cho soldQuantity
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementSoldQuantity(int quantity) {
        this.soldQuantity += quantity; // Tăng số lượng đã bán
        this.quantity -= quantity; // Giảm số lượng còn lại
    }

    // Lưu danh sách sản phẩm vào file
    public static void saveProductsToFile(List<Product> products, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Product product : products) {
                if (product instanceof Laptop) {
                    Laptop laptop = (Laptop) product;
                    writer.write(String.join(",",
                            laptop.getProductId(),
                            laptop.getProductName(),
                            String.valueOf(laptop.getPrice()),
                            String.valueOf(laptop.getQuantity()),
                            laptop.getCpu(),
                            String.valueOf(laptop.getRam()),
                            String.valueOf(laptop.getStorage())));
                } else if (product instanceof DienThoai) {
                    DienThoai phone = (DienThoai) product;
                    writer.write(String.join(",",
                            phone.getProductId(),
                            phone.getProductName(),
                            String.valueOf(phone.getPrice()),
                            String.valueOf(phone.getQuantity()),
                            phone.getScreenSize(),
                            String.valueOf(phone.getBatteryCapacity())));
                }
                writer.newLine();
            }
        }
    }

    // Đọc danh sách sản phẩm từ file
    public static List<Product> loadProductsFromFile(String filename) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) { // Đối với Laptop
                    Product laptop = new Laptop(data[0], data[1], Double.parseDouble(data[2]),
                            Integer.parseInt(data[3]), data[4],
                            Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                    products.add(laptop);
                } else if (data.length == 6) { // Đối với Điện thoại
                    Product phone = new DienThoai(data[0], data[1], Double.parseDouble(data[2]),
                            Integer.parseInt(data[3]), data[4],
                            Integer.parseInt(data[5]));
                    products.add(phone);
                } else {
                    System.out.println("Dữ liệu không hợp lệ trong dòng: " + line);
                }
            }
        }
        return products;
    }
}
