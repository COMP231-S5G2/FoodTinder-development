package comp231.s5g2.tindeappproject.models;

public class Dish {
    private String nome;
    private double price;
    private String description;

    public Dish(String nome, double price, String description) {
        this.nome = nome;
        this.price = price;
        this.description = description;
    }

<<<<<<< HEAD
=======
    public Dish() {
    }

>>>>>>> Felipe
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

<<<<<<< HEAD
    public double getPrice() {
=======
    public Double getPrice() {
>>>>>>> Felipe
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
