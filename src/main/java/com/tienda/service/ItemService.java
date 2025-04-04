/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Item;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anndy
 */
@Service
public class ItemService {

    //se usa una variable de session para 
    //guardar el carrito de compras
    @Autowired
    private HttpSession session;

    //El siguiente metodo crea un 
    //item en la variable de sesion
    //si la variable no existe se crea tambien
    public void save(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la variable ya existia en la session
        if (lista == null) {
            //Si no estaba se crea...
            lista = new ArrayList<>();
        }
        //Se busca en la lista si el producto ya existe..
        boolean existe = false;
        for (Item i : lista) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                existe = true;
                if (i.getCantidad() < i.getExistencias()) {
                    //Aun se puede comprar..
                    i.setCantidad(i.getCantidad() + 1);
                }
                break;
            }
        }
        if (!existe) {//si no esta el producto en la lista, se agrega
            item.setCantidad(1);
            lista.add(item);
        }
        session.setAttribute("listaItems", lista);
    }

    //El siguiente metodo recupera un 
    //item de la lista
    //si no esta retorna null
    public Item getItem(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la variable ya existia en la session
        if (lista == null) {
            return null;
        }
        //Se busca en la lista el producto..
        for (Item i : lista) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                return i;
            }
        }
        return null;
    }
    
    //El siguiente metodo da el total de venta actual de compra en el carrito
    //si no hay da 0
    public double getTotal() {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la variable ya existia en la session
        if (lista == null) {
            return 0;
        }
        //Se reccorre la lista de productos
        double total=0;
        for (Item i : lista) {
            total+=i.getCantidad()*i.getPrecio();
        }
        return total;
    }
    //El siguiente metodo retorna la lista completa
     public List<Item> getItems() {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        return lista;
    }
}
