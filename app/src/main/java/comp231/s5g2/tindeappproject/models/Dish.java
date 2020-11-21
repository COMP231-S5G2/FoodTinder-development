package comp231.s5g2.tindeappproject.models;

import android.media.session.MediaSession;

public class Dish {
    private String nome;
    private double price;
    private String description;
    private String imageAcessToken;

    public Dish(String nome, double price, String description) {
        this.nome = nome;
        this.price = price;
        this.description = description;
    }

    public String getImageAcessToken() {
        return imageAcessToken;
    }

    public void setImageAcessToken(String imageAcessToken) {
        this.imageAcessToken = imageAcessToken;
    }

    public Dish() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrice() {
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
