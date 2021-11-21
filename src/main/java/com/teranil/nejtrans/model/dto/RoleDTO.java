package com.teranil.nejtrans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements Serializable {
    private Long id;
    private String name;
}
