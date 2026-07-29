package com.kdudek.itemsapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "h2")
class ItemsAppApplicationTests {

    @Test
    void contextLoads() {
    }
}
