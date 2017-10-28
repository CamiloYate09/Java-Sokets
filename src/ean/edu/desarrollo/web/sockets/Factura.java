/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ean.edu.desarrollo.web.sockets;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author SALAAL
 */
public class Factura  implements Serializable{
    
    
    private Date fechaFactura;
    private String numeroCliente;
    private Double totalFactura;
    private ArrayList<String> itemCadena = new ArrayList<String>();
    private Integer precio;

    /**
     * @return the fechaFactura
     */
    public Date getFechaFactura() {
        return fechaFactura;
    }

    /**
     * @param fechaFactura the fechaFactura to set
     */
    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    /**
     * @return the numeroCliente
     */
    public String getNumeroCliente() {
        return numeroCliente;
    }

    /**
     * @param numeroCliente the numeroCliente to set
     */
    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    /**
     * @return the totalFactura
     */
    public Double getTotalFactura() {
        return totalFactura;
    }

    /**
     * @param totalFactura the totalFactura to set
     */
    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    /**
     * @return the itemCadena
     */
    public ArrayList<String> getItemCadena() {
        return itemCadena;
    }

    /**
     * @param itemCadena the itemCadena to set
     */
    public void setItemCadena(ArrayList<String> itemCadena) {
        this.itemCadena = itemCadena;
    }
    
    
     @Override
    public String toString(){
        return "************************************************************"+ 
                "\nSU FACTURA" +
                "\nFecha Factura:  "+getFechaFactura()+
                ", \nNo Cliente: "+getNumeroCliente()+
                //",\n Total Factura: "+getTotalFactura()+
                ",\n Items Con su respectivo valor: "+getItemCadena() + 
                "\n Total de la Compra: " + getTotalFactura()+
                "\n***********************************************************";
 
    }
    
    public String objetoSerializado(String mensaje){
        
        itemCadena.add(mensaje);
        
        
        return mensaje;
    }
  
    
    
    public static void main(String[] args) {
        
        Factura factura = new Factura();
        
      String mensaje  = "pera";
        
        
        factura.objetoSerializado(mensaje);
        
       // factura.toString();
        
        
        System.err.println(factura);
        
        
    }

    /**
     * @return the precio
     */
    public Integer getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
    
}