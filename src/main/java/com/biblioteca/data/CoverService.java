/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
public class CoverService {
  
    Logger log;
    public CoverService(){
       log = LogManager.getLogger();
    }
    public byte[] findCover(String ISBN){
       FileInputStream stream;
       /**o limite de tamanho das capas é 600kb    
        * 
        */   
       byte[] coverimage = new byte[600000];
       
       try{stream = new FileInputStream("/home/kkraft/Documents/BibliotecaCapas/" + ISBN + ".jpg" );}
       catch(FileNotFoundException e){log.debug("file not found"); return new byte[1];}
          
       
       try{
           log.debug(coverimage[599999]);
           int teste = 0;
           while(teste != -1){
                teste += stream.read(coverimage,teste,coverimage.length - 1 - teste);
           }    
           //System.out.println(teste);
       }
       catch(IOException e){
            log.debug("Erro lendo a fileinputstream do coverservice"); 
            return new byte[1];
       }
       return coverimage;
    }
}
