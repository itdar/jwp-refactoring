package kitchenpos.ordering.domain;

import kitchenpos.tablegroup.domain.OrderTable;
import kitchenpos.tablegroup.domain.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    @Autowired
    private OrderTableRepository orderTableRepository;

    private Long orderTableId = 1L;
    private String orderStatus = OrderStatus.COOKING.name();
    private LocalDateTime orderedTime = LocalDateTime.now();
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    private OrderTable savedOrderTable;

    @BeforeEach
    void setUp() {
        OrderLineItem orderLineItem1 = new OrderLineItem(1L, 1);
        OrderLineItem orderLineItem2 = new OrderLineItem(3L, 2);
//        orderLineItems.add(orderLineItemRepository.save(orderLineItem1));
//        orderLineItems.add(orderLineItemRepository.save(orderLineItem2));
        orderLineItems.add(orderLineItem1);
        orderLineItems.add(orderLineItem2);

        savedOrderTable = orderTableRepository.save(new OrderTable(1L, null, 5, false));
    }

    @Test
    void create() {
        Ordering order = new Ordering(
                1L,
                savedOrderTable.getId(),
                orderStatus,
                orderedTime,
                orderLineItems);

        orderRepository.save(order);
    }

    @Test
    void findAllTest() {
        // given
        Ordering order = new Ordering(
                1L,
                savedOrderTable.getId(),
                orderStatus,
                orderedTime,
                orderLineItems);
        orderRepository.save(order);

        // when
        List<Ordering> orders = orderRepository.findAll();

        // then
        assertThat(orders).hasSize(1);
    }

}
