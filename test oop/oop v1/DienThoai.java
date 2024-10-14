public class DienThoai extends Product {
    private String screenSize;
    private int batteryCapacity;

    public DienThoai(String productId, String productName, double price, int quantity, String screenSize,
            int batteryCapacity) {
        super(productId, productName, price, quantity);
        this.screenSize = screenSize;
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public void displayProduct() {
        System.out.println("Điện thoại [ID: " + productId + ", Tên: " + productName + ", Giá: " + price + ", Số lượng: "
                + quantity +
                ", Kích thước màn hình: " + screenSize + ", Dung lượng pin: " + batteryCapacity + "mAh]");
    }

    // Getters
    public String getScreenSize() {
        return screenSize;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }
}
