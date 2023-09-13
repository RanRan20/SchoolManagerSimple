/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Users;

import Conexion.Conexion;
import com.mycompany.school.Escuela;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author DELL
 */
 public class Estudiante extends Persona implements Conexion,Escuela{
   private JLabel matriculaLabel, edadLabel;
   private JTextField matriculaTextField, edadTextField;
   public int matricula;
   
//getter setter matricula
 public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
 
    //getter setter cantidadMateria
 @Override
  public void setCantidadMateria(int cantidadMateria){
        this.cantidadMateria = cantidadMateria;
    }
    
 @Override
  public int getCantidadMateria(){
        return cantidadMateria;
    }
    
    private static final String server = "jdbc:sqlserver://schoolp-server.database.windows.net:1433;database=School;user=user1@schoolp-server;password=Lemonjuice123_;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(server);
    }   
    
  //method to see the data in the panel the jframe
 @Override
  public void showInfo(JPanel panelc) {
   

    saveComponents(panelc); 
    matriculaLabel = new JLabel("Matricula:");
    edadLabel = new JLabel("Edad:");
    materiaLabel = new JLabel("Materias cursadas:");
    matriculaTextField = new JTextField();
    edadTextField = new JTextField();
    materiaTextField = new JTextField();

    // Add additional components specific to Estudiante
    panelc.add(matriculaLabel);
    panelc.add(edadLabel);
    panelc.add(matriculaTextField);
    panelc.add(edadTextField);
    panelc.add(materiaLabel);
    panelc.add(materiaTextField);

    // Refresh panel
    panelc.revalidate();
    panelc.repaint();

    // Position, width, and height of textpane, labels, and buttons
    matriculaLabel.setBounds(10, 10, 100, 30); 
    matriculaTextField.setBounds(120, 10, 200, 30);
    nombreLabel.setBounds(10, 50, 100, 30);
    nombreTextField.setBounds(120, 50, 200, 30);
    apellidoLabel.setBounds(10, 90, 100, 30);
    apellidoTextField.setBounds(120, 90, 200, 30); 
    edadLabel.setBounds(10, 130, 100, 30);
    edadTextField.setBounds(120, 130, 200, 30);
    sexoLabel.setBounds(10, 170, 100, 30); 
    mButton.setBounds(120, 170, 100, 30);
    fButton.setBounds(230, 170, 100, 30);
    materiaLabel.setBounds(10, 210, 150, 30);
    materiaTextField.setBounds(160, 210, 160, 30); 
}
  
  
  @Override
    public void getInfo(){
     try {
        setMatricula(Integer.parseInt(matriculaTextField.getText()));
        setNombre(nombreTextField.getText());
        setApellido(apellidoTextField.getText());
        setEdad(Integer.parseInt(edadTextField.getText()));
        setCantidadMateria(Integer.parseInt(materiaTextField.getText()));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "add valid values", "error", JOptionPane.ERROR_MESSAGE);
    }   
    }
  
    
    private JScrollPane tableCreateR(ResultSet resultS) throws SQLException {
    DefaultTableModel modelTable = new DefaultTableModel();
    modelTable.setColumnIdentifiers(new String[]{"Matricula", "Nombre", "Apellido", "Edad", "Sexo", "cantidadMateria"});

    while (resultS.next()) {
        Object[] rowData = {
            resultS.getInt("matricula"),
            resultS.getString("nombre"),
            resultS.getString("apellido"),
            resultS.getInt("edad"),
            resultS.getString("sexo"),
            resultS.getString("cantidadMateria")
        };
        modelTable.addRow(rowData);
    }

    JTable tableT = new JTable(modelTable);
    return new JScrollPane(tableT);
}  
    
    
    
    //method delete
    private JButton createDeleteB(JScrollPane scrollPane) {
    JButton deleteButton = new JButton("Eliminar");
    JTable tb = (JTable) ((JViewport) scrollPane.getComponent(0)).getView();

    deleteButton.addActionListener(new ActionListener() {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            int rS = tb.getSelectedRow();
            if (rS != -1) {
                int matricula = (int) tb.getValueAt(rS, 0);
                try (Connection connection = getConnection();
                    PreparedStatement statementDelete = connection.prepareStatement("DELETE FROM Estudiante WHERE matricula = ?")) {
                    statementDelete.setInt(1, matricula);
                    statementDelete.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tb.getModel();
                    model.removeRow(rS);
                    JOptionPane.showMessageDialog(null, "Data has been deleted sucessfully", "delete", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "error to process SQL " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a record to erase", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    

    return deleteButton;
    
}
   
    
    private JButton createUpdateB(JScrollPane scrollPane) {
    JButton updateB = new JButton("actualizar");
    
    JTable table = (JTable) ((JViewport) scrollPane.getComponent(0)).getView();

    updateB.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int matricula = (int) table.getValueAt(selectedRow, 0);
                String nombre = (String) table.getValueAt(selectedRow, 1);
                String apellido = (String) table.getValueAt(selectedRow, 2);
                int edad = (int) table.getValueAt(selectedRow, 3);
                String sexo = (String) table.getValueAt(selectedRow, 4);
                String cantidadMateriaStr = (String) table.getValueAt(selectedRow, 5);
                int cantidadMateria = Integer.parseInt(cantidadMateriaStr);

                try {
                    Connection connection = getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "UPDATE Estudiante SET nombre = ?, apellido = ?, edad = ?, sexo = ?, cantidadMateria = ? WHERE matricula = ?");
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setString(2, apellido);
                    preparedStatement.setInt(3, edad);
                    preparedStatement.setString(4, sexo);
                    preparedStatement.setInt(5, cantidadMateria);
                    preparedStatement.setInt(6, matricula);

                    int UpdateRows = preparedStatement.executeUpdate();
                    if (UpdateRows > 0) {
                        JOptionPane.showMessageDialog(null, "data has been update sucessfully", "update", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "no student found with that enrollment", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    connection.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error to process SQL " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a ecord to update", "error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    return updateB;
}
    
    
   //methods for Student to database
    
    
    //method to save in database
 @Override
    public void save() {
      
          try (Connection connection = getConnection()) {

            PreparedStatement statementSave = connection.prepareStatement("INSERT INTO Estudiante (matricula, nombre, apellido, edad, sexo,cantidadMateria) VALUES (?, ?, ?, ?, ?,?)");
            statementSave.setInt(1, getMatricula());
            statementSave.setString(2, getNombre());
            statementSave.setString(3, getApellido());
            statementSave.setInt(4, getEdad());
            statementSave.setString(5, getSexo());
            statementSave.setInt(6,getCantidadMateria());
            statementSave.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data inserted successfully", "Congrats", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error to process SQL: " + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

 //method delete to erase the data
 @Override
   public void delete(JPanel panelc) {
    try (Connection connection = getConnection();
         PreparedStatement StatementView = connection.prepareStatement("SELECT * FROM Estudiante")) {

        ResultSet resultS = StatementView .executeQuery();
        JScrollPane scrollP = tableCreateR(resultS);
        JButton bDelete = createDeleteB(scrollP);

        panelc.removeAll();
        panelc.setLayout(new BorderLayout());
        panelc.add(scrollP, BorderLayout.CENTER);
        panelc.add(bDelete, BorderLayout.SOUTH);
        panelc.revalidate();
        panelc.repaint();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error executing SQL query: " + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
    }
}


  //method update to edit the data
 @Override
  public void update(JPanel panelc) {
    try (Connection connection = getConnection();
         PreparedStatement preparedView = connection.prepareStatement(
                 "SELECT * FROM Estudiante")) {

        ResultSet rS = preparedView.executeQuery();

        JScrollPane scrollP = tableCreateR(rS); 

        JButton updateButton = createUpdateB(scrollP);
        
        panelc.removeAll();
        panelc.setLayout(new BorderLayout());
        panelc.add(scrollP, BorderLayout.CENTER);
        panelc.add(updateButton, BorderLayout.SOUTH);
        panelc.revalidate();
        panelc.repaint();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error executing SQL query: " + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
    }
}
    
  
  //method view to see the data
 @Override
   public void view(JPanel panelc) {
    try (Connection connection = getConnection();
         PreparedStatement statementView = connection.prepareStatement("SELECT * FROM Estudiante")) {

        ResultSet rS = statementView.executeQuery();

        JScrollPane scrollP = tableCreateR(rS);

        panelc.removeAll();
        panelc.setLayout(new BorderLayout());
        panelc.add(scrollP, BorderLayout.CENTER);
        panelc.revalidate();
        panelc.repaint();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error executing SQL query: " + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
    }
}
   
   
 @Override
   public void search(JPanel panelc) {
    String search = JOptionPane.showInputDialog(panelc, "Ingrese la matrícula", "Search Student", JOptionPane.QUESTION_MESSAGE);
    
    if (search != null && !search.isEmpty()) {
        try (Connection connection = getConnection();
             PreparedStatement StatementeSearch = connection.prepareStatement(
                     "SELECT * FROM Estudiante WHERE matricula = ?")) {
            
           StatementeSearch.setString(1, search);
            ResultSet resultSet = StatementeSearch.executeQuery();
            JScrollPane scrollP = tableCreateR(resultSet);
            panelc.removeAll();
            panelc.setLayout(new BorderLayout());
            panelc.add(scrollP, BorderLayout.CENTER);
            panelc.revalidate();
            panelc.repaint();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error to process SQL " + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
 }

