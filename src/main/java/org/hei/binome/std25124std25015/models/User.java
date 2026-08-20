package org.hei.binome.std25124std25015.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private String id;
    private String ref;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<CashFlow> clashFlows;
}
