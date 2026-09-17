package com.pragma.powerup.domain.model;

public enum RoleEnum {
    ADMINISTRADOR(1L),
    PROPIETARIO(2L),
    EMPLEADO(3L),
    CLIENTE(4L);

    private final Long id;

    RoleEnum(Long id) {
        this.id=id;
    }

    public Long getId(){
        return id;
    }

    public static RoleEnum fromId(Long id){
        for (RoleEnum role:values()){
            if(role.id.equals(id)){
                return role;
            }
        }
        throw new IllegalArgumentException("Rol no valido "+id);
    }
}
