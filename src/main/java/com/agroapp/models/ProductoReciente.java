package com.agroapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Productos_Recientes")
public class ProductoReciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reciente_id")
    private int recienteId;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public int getRecienteId() {
        return recienteId;
    }

    public void setRecienteId(int recienteId) {
        this.recienteId = recienteId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
