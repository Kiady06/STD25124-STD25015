package org.hei.binome.std25124std25015.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String ref;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<CashFlow> cashFlows;
}
