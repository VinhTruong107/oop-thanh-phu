
// Person.java
import java.util.List;

public abstract class Person {
    protected String name;
    protected String id;

    // Constructor
    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Phương thức trừu tượng
    public abstract void viewProducts(List<Product> products);
}
