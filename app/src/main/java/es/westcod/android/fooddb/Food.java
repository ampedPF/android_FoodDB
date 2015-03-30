package es.westcod.android.fooddb;

/**
 * Created by ampedPF on 29/03/2015.
 */
public class Food {
    private int id;
    private String name;
    private float price;
    private int quantity;

    public Food(int id, String name, Float price, int quantity){
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Food(){
        super();
        // TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return "Food [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
    }

}
