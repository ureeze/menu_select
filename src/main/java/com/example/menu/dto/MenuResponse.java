package com.example.menu.dto;

import com.example.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponse {
    private String name;
    private String address;
    //    private String phone;
    private String placeUrl;
    private String query;
    private String x;
    private String y;

    public MenuResponse(Menu menu) {
        this.name = menu.getPlaceName();
        this.address = menu.getAddressName();
        this.query = menu.getQuery();
        this.placeUrl = menu.getPlaceUrl();
        this.x = menu.getX();
        this.y = menu.getY();
    }
}
