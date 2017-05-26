package com.my.app.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
@Entity
public class OrderPizza {

    @Id
    @GeneratedValue
    private Long order_id;
    @ManyToOne
    private Adress adress;
    @ManyToMany()
    @JoinTable(name="ORDERPIZZA_PIZZA",
            joinColumns={@JoinColumn(name="ORDER_ID")},
            inverseJoinColumns={@JoinColumn(name="PIZZA_ID")})
    private List<Pizza> pizzaList;
    private Double price;
    private Date date;

    @ManyToOne
    private User user;

    public OrderPizza() {
    }

    public OrderPizza(Adress adress, List<Pizza> pizzaList, Double price, Date date) {
        this.adress = adress;
        this.pizzaList = pizzaList;
        this.price = price;
        this.date = date;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public List<Pizza> getPizzaList() {
        return pizzaList;
    }

    public void setPizzaList(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderPizza{" +
                "order_id=" + order_id +
                ", adress=" + adress +
                ", pizzaList=" + pizzaList.get(0).getName() +
                ", price=" + price +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
