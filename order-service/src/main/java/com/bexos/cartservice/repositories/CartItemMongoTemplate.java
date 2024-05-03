package com.bexos.cartservice.repositories;

import com.bexos.cartservice.dto.CartQuantitySum;
import com.bexos.cartservice.dto.CartTotalPriceSum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartItemMongoTemplate {
    private final MongoTemplate mongoTemplate;

    public int getTotalQuantityByOrderId(String orderId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(org.springframework.data.mongodb.core.query.Criteria.where("orderId").is(orderId)),
                Aggregation.group().sum("quantity").as("totalQuantity")
        );

        AggregationResults<CartQuantitySum> aggregationResults =
                mongoTemplate.aggregate(aggregation, "Cart_Items", CartQuantitySum.class);

        List<CartQuantitySum> results = aggregationResults.getMappedResults();
        if (!results.isEmpty()) {
            return results.get(0).getTotalQuantity();
        } else {
            return 0;
        }

    }

    public int getTotalPriceByOrderId(String orderId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(org.springframework.data.mongodb.core.query.Criteria.where("orderId").is(orderId)),
                Aggregation.group().sum("totalPrice").as("totalPrice")
        );

        AggregationResults<CartTotalPriceSum> aggregationResults =
                mongoTemplate.aggregate(aggregation, "Cart_Items", CartTotalPriceSum.class);

        List<CartTotalPriceSum> results = aggregationResults.getMappedResults();
        if (!results.isEmpty()) {
            return results.get(0).getTotalPrice();
        } else {
            return 0;
        }
    }

}

