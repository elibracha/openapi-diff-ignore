package org.openapi.diff.ignore.samples.controllers.v2;

import org.openapi.diff.ignore.samples.petstore.api.StoreApi;
import org.openapi.diff.ignore.samples.petstore.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v2")
public class StoreControllerV2 implements StoreApi {

    @Override
    public ResponseEntity<Void> deleteOrder(Long orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Map<String, Integer>>> getInventory() {
        return null;
    }

    @Override
    public ResponseEntity<Order> getOrderById(Long orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Order> placeOrder(@Valid Order order) {
        return null;
    }
}
