package com.real_JPA.JPA_SHOP.service;

import com.real_JPA.JPA_SHOP.domain.item.Item;
import com.real_JPA.JPA_SHOP.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
