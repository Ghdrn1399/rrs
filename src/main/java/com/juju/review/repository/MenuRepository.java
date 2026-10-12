package com.juju.review.repository;

import com.juju.review.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    public List<MenuEntity> findByRestaurantId(Long restaurantId);

    List<MenuEntity> findAllByRestaurantId(Long restaurantId);
}
