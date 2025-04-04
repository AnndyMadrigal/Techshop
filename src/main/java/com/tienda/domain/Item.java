/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.domain;

import lombok.Data;

/**
 *
 * @author anndy
 */

@Data
public class Item extends Producto {
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public Item(){
        
    }
    
    public Item(Producto p){
        super.setActivo(p.isActivo());
        super.setCategoria(p.getCategoria());
        super.setDescripcion(p.getDescripcion());
        super.setDetalle(p.getDetalle());
        super.setExistencias(p.getExistencias());
        super.setIdProducto(p.getIdProducto());
        super.setPrecio(p.getPrecio());
        super.setRutaImagen(p.getRutaImagen());
        cantidad=0;
    }
}
