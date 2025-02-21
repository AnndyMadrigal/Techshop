
package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Transactional(readOnly=true)
    public List<Producto> getProductos(boolean activos){
        var lista = productoRepository.findAll();
        //Falta algo aca
        return lista;
    }
    
    //Se escribe los metodos de un CRUD Create Read Update Delete
    
    @Transactional(readOnly=true)
    public Producto getProducto(Producto producto){
        
        return productoRepository.findById(producto.getIdProducto()).orElse(null);
    }
    
    @Transactional
    public void delete(Producto producto){
        //Elimina el registro que contiene el id igual a lo pasado en producto.getIdProducto()
         productoRepository.delete(producto);
    }
    
    @Transactional
    public void save(Producto producto){
        //si producto.idProducto esta vacio.. se inserta un registro
        //si producto.idProducto tiene algo.. se modifica ese registro
         productoRepository.save(producto);
    }
    
}
