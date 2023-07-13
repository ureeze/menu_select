package com.example.menu.dto;

import com.example.menu.entity.Menu;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoResponse {
    private String address_name;
    private String category_group_code;
    private String category_group_name;
    private String category_name;
    private String distance;
    private String id;
    private String phone;
    private String place_name;
    private String place_url;
    private String road_address_name;
    private String x;
    private String y;

    public Menu toMenu(String query) {
        return Menu.builder()
                .addressName(address_name)
                .storeId(id)
                .placeName(place_name)
                .placeUrl(place_url)
                .categoryName(category_name)
                .roadAddressName(road_address_name)
                .query(query)
                .x(x)
                .y(y)
                .build();
    }
}