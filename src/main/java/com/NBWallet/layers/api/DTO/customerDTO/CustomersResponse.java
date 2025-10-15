package com.NBWallet.layers.api.DTO.customerDTO;

import com.NBWallet.layers.api.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomersResponse {

    private List<Customer> items;
    private int pageNumber;
    private int totalPages;
    private int totalCount;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
}