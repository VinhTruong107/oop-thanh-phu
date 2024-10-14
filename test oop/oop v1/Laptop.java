public class Laptop extends Product {
    private String cpu;
    private int ram;
    private int storage;

    // Constructor
    public Laptop(String productId, String productName, double price, int quantity, String cpu, int ram, int storage) {
        super(productId, productName, price, quantity);
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
    }

    @Override
    public void displayProduct() {
        System.out.println(
                "Laptop - Mã: " + productId + ", Tên: " + productName + ", Giá: " + price + ", Số lượng: " + quantity +
                        ", CPU: " + cpu + ", RAM: " + ram + "GB, Bộ nhớ: " + storage + "GB");
    }

    // Getters
    public String getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public int getStorage() {
        return storage;
    }
}
