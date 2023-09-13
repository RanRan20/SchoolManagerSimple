/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Users;

import Conexion.Conexion;
import com.mycompany.school.Escuela;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author DELL
 */
public class Maestro extends Persona implements Conexion,Escuela{
private JLabel telefonoLabel;
private JTextField telefonoTextField;
public String telefono;
private JComboBox<String> materiaComboBox;

    private static final String server = "jdbc:sqlserver://schoolp-server.database.windows.net:1433;database=School;user=user1@schoolp-server;password=Lemonjuice123_;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    
    @Override
    public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(server);
    }   
    
     public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
 @Override
    public void showInfo(JPanel panelc) {
    
   saveComponents(panelc); 
   
   telefonoLabel = new JLabel("Telefono:");
   telefonoTextField = new JTextField();  
   materiaComboBox = new JComboBox<>(new String[]{"Español", "Matemáticas", "Historia", "Química", "Inglés"});
   
    panelc.add(telefonoLabel);
    panelc.add(telefonoTextField);
    panelc.add(materiaComboBox);
    
    //refresh//
    panelc.revalidate();
    panelc.repaint();
    
      
   //position//width//heigth textpane and labels and buttons
    nombreLabel.setBounds(10, 10, 100, 30); 
    nombreTextField.setBounds(120, 10, 200, 30);
    apellidoLabel.setBounds(10, 50, 100, 30);
    apellidoTextField.setBounds(120, 50, 200, 30);
    sexoLabel.setBounds(10, 90, 100, 30);
    mButton.setBounds(120, 90, 100, 30);
    fButton.setBounds(230, 90, 100, 30);
    telefonoLabel.setBounds(10, 130, 100, 30);
    telefonoTextField.setBounds(120, 130, 200, 30);
    materiaLabel.setBounds(10, 170, 150, 30);
    materiaComboBox.setBounds(160, 170, 160, 30);
    


}
  
    private JScrollPane tableCreateR(ResultSet resultS) throws SQLException {
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new String[]{"ID", "Nombre", "Apellido", "Sexo", "Telefono", "Materia"});

    while (resultS.next()) {
        Object[] rowData = {
            resultS.getInt("ID"),
            resultS.getString("MaestroNombre"),
            resultS.getString("Apellido"),
            resultS.getString("Sexo"),
            resultS.getString("Telefono"),
            resultS.getString("MateriaNombre")
        };
        model.addRow(rowData);
    }

    JTable table = new JTable(model);
    return new JScrollPane(table);
}

    private JButton createDeleteB(JScrollPane scrollPane) {
    JButton button = new JButton("eliminar");
    button.addActionListener(e -> {
        int selectedRow = ((JTable) scrollPane.getViewport().getView()).getSelectedRow();
        if (selectedRow != -1) {
            int maestroID = (int) ((JTable) scrollPane.getViewport().getView()).getValueAt(selectedRow, 0);
            try {
                Connection connection = getConnection(); 
                PreparedStatement statementDelete = connection.prepareStatement("DELETE FROM Maestro WHERE ID = ?");
                statementDelete.setInt(1, maestroID);
                statementDelete.executeUpdate();
                JOptionPane.showMessageDialog(null, "record deleted sucessfully", "Eliminado", JOptionPane.INFORMATION_MESSAGE);         
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "error to delete a record" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a record to delete", "Error", JOptionPane.WARNING_MESSAGE);
        }
    });
    return button;
}
    
  private JButton createUpdateB(JScrollPane scrollPane) {
    JButton button = new JButton("Actualizar");
    button.addActionListener(e -> {
        int selectedRow = ((JTable) scrollPane.getViewport().getView()).getSelectedRow();
        if (selectedRow != -1) {
            int maestroID = (int) ((JTable) scrollPane.getViewport().getView()).getValueAt(selectedRow, 0);
            String newName = JOptionPane.showInputDialog(scrollPane, "Nuevo nombre:");
            if (newName != null && !newName.isEmpty()) {
                try (Connection connection = getConnection()) {
                    PreparedStatement statementUpdate = connection.prepareStatement("UPDATE Maestro SET Nombre = ? WHERE ID = ?");
                    statementUpdate.setString(1, newName);
                    statementUpdate.setInt(2, maestroID);
                    statementUpdate.executeUpdate();
                    JOptionPane.showMessageDialog(null, "record update succesfully", "update", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error to update enrollment record " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío", "error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a record to update", "error", JOptionPane.WARNING_MESSAGE);
        }
    });
    return button;
}
      //methods for Maestro to database
    
 
 @Override
  public void save() {
    try (Connection connection = getConnection()) {
        
        // insert in table materia
        PreparedStatement statementMateria = connection.prepareStatement("INSERT INTO Materia (Nombre) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        statementMateria.setString(1, getMateria());
        statementMateria.executeUpdate();

        
        ResultSet generatedKeys = statementMateria.getGeneratedKeys();
        int idMateria = -1;
        if (generatedKeys.next()) {
            idMateria = generatedKeys.getInt(1);
        }

        //insert table Maestro
        PreparedStatement statementMaestro = connection.prepareStatement("INSERT INTO Maestro (nombre, apellido, sexo, telefono, ID_Materia) VALUES (?, ?, ?, ?, ?)");
        statementMaestro.setString(1, getNombre());
        statementMaestro.setString(2, getApellido());
        statementMaestro.setString(3, getSexo());
        statementMaestro.setString(4, getTelefono());
        statementMaestro.setInt(5, idMateria);
        statementMaestro.executeUpdate();

        JOptionPane.showMessageDialog(null, "Data inserted successfully", "Congrats", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error to process SQL: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
  
 @Override
   public void getInfo() {
    try {
        setNombre(nombreTextField.getText());
        setApellido(apellidoTextField.getText());
        setSexo(mButton.isSelected() ? "Masculino" : "Femenino");
        setTelefono(telefonoTextField.getText());
        String selectedMateria = (String) materiaComboBox.getSelectedItem();
        setMateria(selectedMateria);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Add valid values", "error", JOptionPane.ERROR_MESSAGE);
    }
}
       
    
 @Override
    public void delete(JPanel panelC) {
    try (Connection connection = getConnection();
         PreparedStatement statementView = connection.prepareStatement(
                 "SELECT m.ID, m.Nombre AS MaestroNombre, m.Apellido, m.Sexo, m.Telefono, mt.Nombre AS MateriaNombre " +
                 "FROM Maestro m " +
                 "LEFT JOIN Materia mt ON m.ID_Materia = mt.ID")) {

        ResultSet rS = statementView.executeQuery();

        JScrollPane scrollP = tableCreateR(rS);
        JButton bDelete = createDeleteB(scrollP);

        panelC.removeAll();
        panelC.setLayout(new BorderLayout());
        panelC.add(scrollP, BorderLayout.CENTER);
        panelC.add(bDelete, BorderLayout.SOUTH);
        panelC.revalidate();
        panelC.repaint();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error to process SQL: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
 @Override
   public void update(JPanel panelc) {
    try (Connection connection = getConnection()) {
        PreparedStatement statementView = connection.prepareStatement(
                "SELECT m.ID, m.Nombre AS MaestroNombre, m.Apellido, m.Sexo, m.Telefono, mt.Nombre AS MateriaNombre " +
                "FROM Maestro m " +
                "LEFT JOIN Materia mt ON m.ID_Materia = mt.ID");

        ResultSet rS = statementView.executeQuery();

        JScrollPane scrollP = tableCreateR(rS);
        JButton bUpdate = createUpdateB(scrollP); 

        panelc.removeAll();
        panelc.setLayout(new BorderLayout());
        panelc.add(scrollP, BorderLayout.CENTER);
        panelc.add(bUpdate, BorderLayout.SOUTH);
        panelc.revalidate();
        panelc.repaint();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error to process SQL: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
 @Override
   public void view(JPanel panelC) {
    try (Connection connection = getConnection();
         PreparedStatement statementView = connection.prepareStatement(
                 "SELECT m.ID, m.Nombre AS MaestroNombre, m.Apellido, m.Sexo, m.Telefono, mt.Nombre AS MateriaNombre " +
                 "FROM Maestro m " +
                 "LEFT JOIN Materia mt ON m.ID_Materia = mt.ID")) {

        ResultSet rS = statementView.executeQuery();

        JScrollPane scrollP = tableCreateR(rS);

        panelC.removeAll();
        panelC.setLayout(new BorderLayout());
        panelC.add(scrollP, BorderLayout.CENTER);
        panelC.revalidate();
        panelC.repaint();

    } catch (SQLException e) {
         e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error to process SQL: " + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        
    }
}
   
 @Override
   public void search(JPanel panelc) {
    String searchNombre = JOptionPane.showInputDialog(panelc, "Ingrese el Nombre del docente", "Buscar Maestro", JOptionPane.QUESTION_MESSAGE);
    
    if (searchNombre != null && !searchNombre.isEmpty()) {
        try (Connection connection = getConnection();
             PreparedStatement statementSearch = connection.prepareStatement(
                     "SELECT m.ID, m.Nombre AS MaestroNombre, m.Apellido, m.Sexo, m.Telefono, mt.Nombre AS MateriaNombre " +
                     "FROM Maestro m " +
                     "LEFT JOIN Materia mt ON m.ID_Materia = mt.ID " +
                     "WHERE m.Nombre LIKE ?")) {
            
            statementSearch.setString(1, "%" + searchNombre + "%");
            ResultSet resultSet = statementSearch.executeQuery();

            JScrollPane scrollP = tableCreateR(resultSet);
            panelc.removeAll();
            panelc.setLayout(new BorderLayout());
            panelc.add(scrollP, BorderLayout.CENTER);
            panelc.revalidate();
            panelc.repaint();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error to process SQL " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
}