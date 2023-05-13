package org.launchcode.qleanquotes;

import com.squareup.square.api.CustomersApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.api.OrdersApi;
import com.squareup.square.api.PaymentsApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SquareConfig {
    @Bean
    public SquareClient squareClient(LocationsApi locationsApi, PaymentsApi paymentsApi, CustomersApi customersApi, OrdersApi ordersApi) {
        return new SquareClient(locationsApi, paymentsApi, customersApi, ordersApi);
    }

}
