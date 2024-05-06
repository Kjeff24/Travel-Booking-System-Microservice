package com.bexos.bookingservice.feign;

import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CATEGORY-SERVICE")
public interface CategoryClient {
    @GetMapping("/api/category-service/exists-by-id/{id}")
    boolean existsCategoryById(@PathVariable String id);

    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable String id);
}
