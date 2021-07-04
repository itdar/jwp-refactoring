package kitchenpos.ordering.ui;

import kitchenpos.ordering.application.OrderService;
import kitchenpos.ordering.domain.Ordering;
import kitchenpos.ordering.dto.OrderRequest;
import kitchenpos.ordering.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponse> create(@RequestBody final OrderRequest orderRequest) {
        final OrderResponse orderResponse = orderService.create(orderRequest);
        final URI uri = URI.create("/api/orders/" + orderResponse.getId());
        return ResponseEntity.created(uri).body(orderResponse);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<Ordering>> list() {
        return ResponseEntity.ok().body(orderService.list());
    }

    @PutMapping("/api/orders/{orderId}/order-status")
    public ResponseEntity<Ordering> changeOrderStatus(
            @PathVariable final Long orderId,
            @RequestBody final OrderRequest orderRequest
    ) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderRequest.getOrderStatus()));
    }
}
