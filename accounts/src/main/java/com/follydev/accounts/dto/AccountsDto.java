package com.follydev.accounts.dto;

import com.follydev.accounts.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.DataAmount;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDto extends BaseEntity {

    @NotEmpty(message = "Account number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number should be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account type is required")
    private String accountType;

    @NotEmpty(message = "Branch name is required")
    private String branchAddress;
}
