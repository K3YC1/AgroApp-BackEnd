package com.agroapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direccion_entrega_id")
    private int direccionEntregaId;

    private String direccion;

    private String ciudad;

    private String distrito;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    public int getDireccionEntregaId() {
        return direccionEntregaId;
    }

    public void setDireccionEntregaId(int direccionEntregaId) {
        this.direccionEntregaId = direccionEntregaId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
