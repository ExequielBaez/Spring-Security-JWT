package com.jwt.springsecurity.persistence.util;

import lombok.Getter;
import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {

    ADMINISTRATOR(Arrays.asList(
            RolePermission.READ_ALL_PRODUCTS,
            RolePermission.READ_ONE_PRODUCT,
            RolePermission.CREATE_ONE_PRODUCT,
            RolePermission.UPDATE_ONE_PRODUCT,
            RolePermission.DISABLE_ONE_PRODUCT,

            RolePermission.READ_ALL_CATEGORIES,
            RolePermission.READ_ONE_CATEGORY,
            RolePermission.CREATE_ONE_CATEGORY,
            RolePermission.UPDATE_ONE_CATEGORY,
            RolePermission.DISABLE_ONE_CATEGORY,

            RolePermission.READ_MY_PROFILE
    )),

    ASSISTANCE_ADMINISTRATOR(Arrays.asList(
          RolePermission.READ_ALL_PRODUCTS,
          RolePermission.READ_ONE_PRODUCT,
          RolePermission.UPDATE_ONE_PRODUCT,

          RolePermission.READ_ALL_CATEGORIES,
          RolePermission.READ_ONE_CATEGORY,
          RolePermission.DISABLE_ONE_CATEGORY,

          RolePermission.READ_MY_PROFILE
     )),

    COSTUMER(Arrays.asList(
          RolePermission.READ_MY_PROFILE
    ));


    private List<RolePermission> permissions; //atributo list pertenciente al enum

    Role(List<RolePermission> permissions) { //constructor del enum
        this.permissions = permissions;
    }
}
