/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;

import com.tienda.domain.Item;
import com.tienda.domain.Producto;
import com.tienda.service.ItemService;
import com.tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author anndy
 */
@Controller
@RequestMapping("/carrito")
public class CarritoController {
    
    
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductoService productoService;
    
    @GetMapping("/agregar/{idProducto}")
    public ModelAndView agregar(Model model, Item item){
        Item item2 = itemService.getItem(item);
        
        //si el producto aun no esta en la lista, se recupera la info de la
        //tabla producto
        if(item2 == null){
            //Si no esta se busca el producto y se crea item2
            Producto producto = productoService.getProducto(item);
            item2 = new Item(producto);
        }
        
        //se crea o actualiza el producto en la lista
        itemService.save(item2);
        
        var lista = itemService.getItems();
        var totalCompra = itemService.getTotal();
        model.addAttribute("listaItems", lista);
        model.addAttribute("totalCompra", totalCompra);
        model.addAttribute("totalProductos", lista.size());
        
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = itemService.getItems();
        var totalCompra = itemService.getTotal();
        model.addAttribute("listaItems", lista);
        model.addAttribute("totalCompra", totalCompra);
        return "/carrito/listado";
    }
    
    @GetMapping("/eliminar/{idProducto}")
    public String eliminar(Model model, Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }
    
    @GetMapping("/modificar/{idProducto}")
    public String modificar(Model model, Item item) {
        item = itemService.getItem(item);
        model.addAttribute("item",item);
        return "/carrito/modifica";
    }
    
    @PostMapping("/guardar")
    public String guardar(Item item) {
        itemService.update(item);
        
        return "redirect:/carrito/listado";
    }
}
