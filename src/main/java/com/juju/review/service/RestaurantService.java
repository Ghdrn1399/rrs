package com.juju.review.service;

import com.juju.review.api.request.CreateAndEditRestaurantRequest;
import com.juju.review.api.response.RestaurantDetailView;
import com.juju.review.api.response.RestaurantView;
import com.juju.review.model.MenuEntity;
import com.juju.review.model.RestaurantEntity;
import com.juju.review.repository.MenuRepository;
import com.juju.review.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;


    @Transactional
    public RestaurantEntity createRestaurant(
        CreateAndEditRestaurantRequest request
    ){
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createAt(ZonedDateTime.now())
                .updateAt(ZonedDateTime.now())
                .build();
        restaurantRepository.save(restaurant);


        request.getMenus().forEach(menu -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurant.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });

        return restaurant;
    }

    @Transactional
    public void editRestaurant(
            Long restaurantId,
            CreateAndEditRestaurantRequest request
    ){
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("없는 아이디 입니다."));
        restaurant.changeNameAndAddress(request.getName(), request.getAddress());
        restaurantRepository.save(restaurant);

        // 기존 메뉴 삭제
        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);

        // 새 메뉴 저장
        request.getMenus().forEach(menu -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurantId)
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();
            menuRepository.save(menuEntity);
        });
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId){
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        restaurantRepository.delete(restaurant);

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);

    }
    public List<RestaurantView> getAllRestaurants(){
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();

        return restaurants.stream().map(restaurant ->RestaurantView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreateAt())
                .updatedAt(restaurant.getUpdateAt())
                .build()
        ).toList();
    }


    @Transactional(readOnly = true)
    public RestaurantDetailView getRestaurantDetail(Long restaurantId){
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);

        return RestaurantDetailView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreateAt())
                .updatedAt(restaurant.getUpdateAt())
                .menus(menus.stream().map(menu -> RestaurantDetailView.Menu.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .createdAt(menu.getCreatedAt())
                        .updatedAt(menu.getUpdatedAt())
                        .build()
                ).toList())
                .build();
    }
}
