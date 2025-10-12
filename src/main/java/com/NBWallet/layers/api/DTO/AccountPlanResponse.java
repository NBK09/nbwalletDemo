package com.NBWallet.layers.api.DTO;

import com.NBWallet.layers.api.models.AccountPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountPlanResponse {
        private List<AccountPlan> items;
        private int pageNumber;
        private int totalPages;
        private int totalCount;
        private boolean hasPreviousPage;
        private boolean hasNextPage;

}
