package com.real_JPA.JPA_SHOP.domain.item;

import com.real_JPA.JPA_SHOP.domain.Category;
import com.real_JPA.JPA_SHOP.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // == 비즈니스 로직 == // 응집력을 위해서 엔티티 안에서 로직을 짬.

    // 재고수량 증가 로직
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고수량 감소 로직
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("remove quantity is more than current quantity");
        }
        this.stockQuantity = restStock;
    }
}
