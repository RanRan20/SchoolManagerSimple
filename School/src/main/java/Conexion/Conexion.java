/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public interface Conexion {
    Connection getConnection() throws SQLException; 
}
