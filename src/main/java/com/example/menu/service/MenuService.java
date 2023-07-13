package com.example.menu.service;

import com.example.menu.dto.KakaoResponse;
import com.example.menu.dto.MenuResponse;
import com.example.menu.entity.Menu;
import com.example.menu.entity.MenuRepository;
import com.example.menu.entity.MenuType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public List<KakaoResponse> updateDb() {
        List<String> menuType = List.of("돈까스", "면", "백반", "쌀국수", "감자탕", "햄버거", "덮밥", "찌개", "짜장면", "닭", "청국장", "곰탕", "국밥");
        List<KakaoResponse> result = new ArrayList<>();
        for (String query : menuType) {
            result.addAll(kakaoApi(query));
        }
        return result;
    }

    private List<KakaoResponse> kakaoApi(String query) {
//        String query = "백반";
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?"
                + "y=" + "37.251443"
                + "&x=" + "127.076705"
                + "&radius=" + "700"
                + "&size=" + "15"
                + "&page=" + "1"
                + "&category_group_code=" + "FD6"
                + "&query=" + query;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 58b7690557953d6f4e7f9c6bf384895f");
        headers.set("Host", "dapi.kakao.com");

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return jsonToEntity(response.getBody(), query);
    }

    private List<KakaoResponse> jsonToEntity(String response, String query) {
        JsonObject object = (JsonObject) JsonParser.parseString(response);
        JsonArray array = (JsonArray) object.get("documents");

        Gson gson = new Gson();
        List<KakaoResponse> list = gson.fromJson(array, new TypeToken<List<KakaoResponse>>() {
        }.getType());

        List<Menu> menuList = list.stream()
                .map(kakaoResponse -> kakaoResponse.toMenu(query))
                .collect(Collectors.toList());

        menuRepository.saveAll(menuList);

        return list;

//        JsonObject object1 = (JsonObject) array.get(1);
//
//
//        KakaoResponse kakaoResponse = gson.fromJson(object1, KakaoResponse.class);
//        System.out.println(kakaoResponse.toString());
    }

    @Transactional
    public MenuResponse search(String name) {
        List<Menu> list = menuRepository.findByQuery(name);
        List<MenuResponse> menuResponseList = list.stream()
                .map(menu -> new MenuResponse(menu))
                .collect(Collectors.toList());

        int size = menuResponseList.size();
        Random random = new Random();

        return menuResponseList.get(random.nextInt(size));
    }
}