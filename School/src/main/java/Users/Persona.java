/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Users;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.layout.Border;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


/**
 *
 * @author DELL
 */
public abstract class Persona {
  protected JLabel nombreLabel, apellidoLabel, sexoLabel, materiaLabel;
  protected JTextField nombreTextField, apellidoTextField, materiaTextField;
  protected JRadioButton mButton, fButton;
  protected ButtonGroup sexoGroup;   
  protected Border border;
    
    public int id;
    public String nombre;
    public String apellido;
    public int edad;
    public String sexo;
    public String materia;
    public int cantidadMateria;
    
     
      protected void saveComponents(JPanel panelc) {
        
          //component configuration
        nombreLabel = new JLabel("Nombre:");
        apellidoLabel = new JLabel("Apellido:");
        sexoLabel = new JLabel("Sexo:");
        materiaLabel = new JLabel("Materia:");

        nombreTextField = new JTextField();
        apellidoTextField = new JTextField();
        materiaTextField = new JTextField();
        
        
        
        mButton = new JRadioButton("Masculino");
        fButton = new JRadioButton("Femenino");

        sexoGroup = new ButtonGroup();
        sexoGroup.add(mButton);
        sexoGroup.add(fButton);

        panelc.add(nombreLabel);
        panelc.add(apellidoLabel);
        panelc.add(sexoLabel);
        panelc.add(materiaLabel);
        panelc.add(nombreTextField);
        panelc.add(apellidoTextField);
        panelc.add(materiaTextField);
        panelc.add(mButton);
        panelc.add(fButton);
        
        
         //color transparent to buttons 
    
        mButton.setOpaque(false);
        mButton.setBorderPainted(false);
        fButton.setOpaque(false);
        fButton.setBorderPainted(false);
          
        mButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String sexo = "Masculino";
               setSexo(sexo);   
            }
        });

        fButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String sexo = "Femenino";
                setSexo(sexo);
            }
        });
    }


      // Getters y setters

      
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public void setMateria(String materia){
        this.materia = materia;
    }
    
    public String getMateria(){
        return materia;
    }
    
   
    public void setCantidadMateria(int cantidadMateria){
        this.cantidadMateria = cantidadMateria;
    }
    
    public int getCantidadMateria(){
      return cantidadMateria;
    } 
}
