package com.lblaga.demostore.configuration;

import com.lblaga.demostore.misc.Round;
import com.lblaga.demostore.model.Product;
import com.lblaga.demostore.service.OrderService;
import com.lblaga.demostore.service.ProductService;
import com.lblaga.demostore.transfer.OrderItemRequest;
import com.lblaga.demostore.transfer.OrderRequest;
import com.lblaga.demostore.transfer.ProductRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Creates demo data
 */
@Configuration
public class DemoDataConfig {

    @Value("${demostore.demodata}")
    private boolean demodata;

    @Bean
    public CommandLineRunner runner(ProductService productService, OrderService orderService) {
        // create data for testing 15 products, 10 orders with 1..5 order items
        return args -> createDemoData(productService, orderService, 15, 10, 5, 4);
    }

    private void createDemoData(ProductService productService, OrderService orderService, int prodSize, int orderSize,
                                int maxItems, int maxAmount) {
        if (demodata) {
            List<Product> products = new ArrayList<>();

            IntStream.range(0, prodSize).forEach(i -> {
                ProductRequest product = new ProductRequest(String.format("Product %d", (i + 1)),
                        Round.round(Math.random() * 100, 2));
                products.add(productService.create(product));
            });

            IntStream.range(0, orderSize).forEach(i -> {
                List<OrderItemRequest> orderItems = new ArrayList<>();

                // generate 1..5 order items
                int itemNr = (int) ((Math.random() * maxItems) + 1);
                IntStream.range(0, itemNr).forEach(j -> {
                    boolean found;
                    do {
                        int productIndex = (int) (Math.random() * products.size());
                        Product product = products.get(productIndex);

                        found = false;
                        for (OrderItemRequest orderItem : orderItems) {
                            if (orderItem.getProductId().equals(product.getId())) {
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            orderItems.add(new OrderItemRequest(product.getId(), (long) (Math.random() * maxAmount + 1)));
                        }
                    } while (found);
                });

                orderService.create(new OrderRequest(String.format("person%d@email.com", i + 1), orderItems));
            });
            System.out.println("Demo data created, ready.");
        } else {
            System.out.println("Demo data NOT created, ready.");
        }
    }

}
