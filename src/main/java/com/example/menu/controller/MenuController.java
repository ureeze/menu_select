package com.example.menu.controller;

import com.example.menu.dto.KakaoResponse;
import com.example.menu.dto.MenuResponse;
import com.example.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/update")
    public ResponseEntity<List<KakaoResponse>> dbUpdate() {
        return ResponseEntity.ok(menuService.updateDb());
    }

    @CrossOrigin
    @GetMapping("/search/{name}")
    public ResponseEntity<MenuResponse> search(@PathVariable("name") String name) {
        return ResponseEntity.ok(menuService.search(name));
    }
}
