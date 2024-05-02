package com.bexos.cartservice.repositories;

import com.bexos.cartservice.dto.QuantitySum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CartItemMongoTemplate {
    private final MongoTemplate mongoTemplate;

    public int getTotalQuantityByOrderId(String orderId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(org.springframework.data.mongodb.core.query.Criteria.where("orderId").is(orderId)),
                Aggregation.group().sum("quantity").as("totalQuantity")
        );

        AggregationResults<QuantitySum> aggregationResults =
                mongoTemplate.aggregate(aggregation, "Cart_Items", QuantitySum.class);

        List<QuantitySum> results = aggregationResults.getMappedResults();
        if (!results.isEmpty()) {
            return results.get(0).getTotalQuantity();
        } else {
            return 0;
        }
    }

}

