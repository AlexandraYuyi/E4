/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.GridBagConstraints;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author alexa
 * Observaciaun: era mejor manejar las cedulas en la base de datos como strings tambien, y a la hora de chequear convertirlas a int, 
 * porque da error NumberFormatException cuando el textfield esta vacio, y si fuera string no pasaria, pero cambiar el tipo de dato
 * en la bd y cambiar todos los queries daba flojera. Pero hubiera quedado mejor el CRUD, en cuanto a manejo de errores. 
 */
public class Ventana extends JFrame implements ActionListener, KeyListener, FocusListener {

    conexionSQL con = new conexionSQL();
    Connection cn = con.conexion();

    private Container c;
    private JPanel p1;
    private JPanel p2;
    private JTable tabla;
    private JLabel nom;
    private JTextField tfnom;
    private JLabel apellido;
    private JTextField tfapellido;
    private JLabel cedula;
    private JTextField tfcedula;
    private JLabel telefono;
    private JTextField tftelefono;
    private JLabel email;
    private JTextField tfemail;
    private JLabel cumple;
    private JLabel foto;

    private JButton nuevo;

    private JButton guardar;

    private JButton editar;

    private JButton borrar;

    private JButton select;

    private JFileChooser archivo;

    private GridBagConstraints gbc;
    private Insets inse;

    private JDateChooser fecha;

    private JLabel id;
    private JTextField tfid;

    private JScrollPane scrollpane;

    private String Ruta;
    
    private JLabel warningCedula;
    private JLabel warningTelefono;
    private JLabel warningEmail;
    private JLabel warningVacio;

    public Ventana() {

        super("Registro de Personas");
        this.setSize(1500, 1000);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        c = this.getContentPane();
        c.setLayout(null);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(30, 40, 500, 880);
        p1.setBackground(Color.white);

        id = new JLabel("ID:");
        id.setFont(new Font("Verdana", Font.BOLD, 15));
        id.setBounds(40, 40, 30, 20);
        p1.add(id);

        tfid = new JTextField(3);
        tfid.setFont(new Font("Verdana", Font.BOLD, 15));
        tfid.setBounds(75, 37, 40, 30);
        tfid.setEnabled(false);
        tfid.setHorizontalAlignment(JTextField.CENTER);
        p1.add(tfid);

        foto = new JLabel();
        foto.setBounds(150, 40, 200, 200);
        p1.add(foto);
        Ruta = "C:/Users/alexa/OneDrive/Documentos/NetBeansProjects/Registro/contact.png";
        Image mImagen = new ImageIcon(Ruta).getImage();
        ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH));
        foto.setIcon(mIcono);

        select = new JButton("Seleccionar imagen");
        select.setBounds(170, 250, 150, 30);
        select.addActionListener(this);
        p1.add(select);
        nom = new JLabel("Nombre: ");
        nom.setFont(new Font("Verdana", Font.BOLD, 15));
        nom.setBounds(40, 300, 80, 40);

        p1.add(nom);

        tfnom = new JTextField(45);
        tfnom.setFont(new Font("Verdana", Font.BOLD, 15));
        tfnom.setBounds(130, 307, 300, 30);
        tfnom.addKeyListener(this);

        p1.add(tfnom);

        apellido = new JLabel("Apellido: ");
        apellido.setFont(new Font("Verdana", Font.BOLD, 15));
        apellido.setBounds(40, 380, 80, 40);
        p1.add(apellido);

        tfapellido = new JTextField(45);
        tfapellido.setFont(new Font("Verdana", Font.BOLD, 15));
        tfapellido.setBounds(130, 387, 300, 30);
        tfapellido.addKeyListener(this);
        p1.add(tfapellido);

        cedula = new JLabel("Cédula: ");
        cedula.setFont(new Font("Verdana", Font.BOLD, 15));
        cedula.setBounds(40, 460, 80, 40);
        p1.add(cedula);

        tfcedula = new JTextField(45);
        tfcedula.setFont(new Font("Verdana", Font.BOLD, 15));
        tfcedula.setBounds(130, 467, 300, 30);
        tfcedula.addKeyListener(this);
        p1.add(tfcedula);

        telefono = new JLabel("Teléfono: ");
        telefono.setFont(new Font("Verdana", Font.BOLD, 15));
        telefono.setBounds(40, 540, 85, 40);
        p1.add(telefono);

        tftelefono = new JTextField(45);
        tftelefono.setFont(new Font("Verdana", Font.BOLD, 15));
        tftelefono.setBounds(130, 547, 300, 30);
        tftelefono.addKeyListener(this);
        p1.add(tftelefono);

        email = new JLabel("Email: ");
        email.setFont(new Font("Verdana", Font.BOLD, 15));
        email.setBounds(40, 630, 85, 40);
        p1.add(email);

        tfemail = new JTextField(45);
        tfemail.setFont(new Font("Verdana", Font.BOLD, 15));
        tfemail.setBounds(130, 637, 300, 30);
        tfemail.addKeyListener(this);
        p1.add(tfemail);

        cumple = new JLabel("Fecha de Nacimiento: ");
        cumple.setFont(new Font("Verdana", Font.BOLD, 15));
        cumple.setBounds(40, 720, 200, 40);
        p1.add(cumple);

        fecha = new JDateChooser();
        fecha.setBounds(300, 727, 120, 30);
        p1.add(fecha);

        Image imgNew = new ImageIcon("C:/Users/alexa/OneDrive/Documentos/NetBeansProjects/Registro/clean.png").getImage();
        ImageIcon iconNew = new ImageIcon(imgNew.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        nuevo = new JButton(iconNew);
        nuevo.setBounds(40, 800, 70, 70);
        nuevo.addActionListener(this);
        p1.add(nuevo);

        Image imgEdit = new ImageIcon("C:/Users/alexa/OneDrive/Documentos/NetBeansProjects/Registro/user-avatar.png").getImage();
        ImageIcon iconEdit = new ImageIcon(imgEdit.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        editar = new JButton(iconEdit);
        editar.setBounds(150, 800, 70, 70);
        editar.addActionListener(this);
        p1.add(editar);

        Image imgSave = new ImageIcon("C:/Users/alexa/OneDrive/Documentos/NetBeansProjects/Registro/add.png").getImage();
        ImageIcon iconSave = new ImageIcon(imgSave.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        guardar = new JButton(iconSave);
        guardar.setBounds(260, 800, 70, 70);
        guardar.addActionListener(this);
        p1.add(guardar);

        Image imgDel = new ImageIcon("C:/Users/alexa/OneDrive/Documentos/NetBeansProjects/Registro/delete.png").getImage();
        ImageIcon iconDel = new ImageIcon(imgDel.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        borrar = new JButton(iconDel);
        borrar.setBounds(370, 800, 70, 70);
        borrar.addActionListener(this);
        p1.add(borrar);
        
        
        warningCedula = new JLabel("Cédula inválida o ya existente, por favor verifique");
        warningCedula.setFont(new Font("Verdana", Font.BOLD, 15));
        warningCedula.setForeground(Color.red);
        warningCedula.setBounds(30, 494, 415, 30);
        warningCedula.setVisible(false);
        p1.add(warningCedula);
        
        warningTelefono = new JLabel("Teléfono inválido, por favor verifique");
        warningTelefono.setFont(new Font("Verdana", Font.BOLD, 15));
        warningTelefono.setForeground(Color.red);
        warningTelefono.setBounds(130, 574, 300, 30);
        warningTelefono.setVisible(false);
        p1.add(warningTelefono);
        
        warningEmail = new JLabel("Email inválido, por favor verifique");
        warningEmail.setFont(new Font("Verdana", Font.BOLD, 15));
        warningEmail.setForeground(Color.red);
        warningEmail.setBounds(140, 664, 305, 30);
        warningEmail.setVisible(false);
        p1.add(warningEmail);
        
        warningVacio = new JLabel("Ningún campo debe estar vacío, por favor llénelos");
        warningVacio.setFont(new Font("Verdana", Font.BOLD, 15));
        warningVacio.setForeground(Color.red);
        warningVacio.setBounds(40, 760, 420, 30);
        warningVacio.setVisible(false);
        p1.add(warningVacio);
        
        c.add(p1);

        tabla = new JTable();
        scrollpane = new JScrollPane(tabla);
        scrollpane.setBounds(560, 40, 899, 880);

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.rowAtPoint(e.getPoint());
                Ruta = tabla.getValueAt(fila, 7).toString();
                Image mImagen = new ImageIcon(Ruta).getImage();
                ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH));
                foto.setIcon(mIcono);

                tfid.setText(tabla.getValueAt(fila, 0).toString());
                tfnom.setText(tabla.getValueAt(fila, 1).toString());
                tfapellido.setText(tabla.getValueAt(fila, 2).toString());
                tfcedula.setText(tabla.getValueAt(fila, 3).toString());

                String cumple = tabla.getValueAt(fila, 4).toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(cumple);
                    fecha.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }

                tftelefono.setText(tabla.getValueAt(fila, 5).toString());
                tfemail.setText(tabla.getValueAt(fila, 6).toString());

            }
        });
        c.add(scrollpane);

        mostrarPersonas();
    }

    public void insertarPersona() {

        LocalDate dia = fecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {

            String consulta = "insert into persona (nom,ap,ced,tel,email,fecha,foto) values (?,?,?,?,?,?,?)";
            CallableStatement cs = cn.prepareCall(consulta);

            cs.setString(1, tfnom.getText());
            cs.setString(2, tfapellido.getText());
            cs.setInt(3, Integer.parseInt(tfcedula.getText()));
            cs.setString(4, tftelefono.getText());
            cs.setString(5, tfemail.getText());
            cs.setDate(6, java.sql.Date.valueOf(dia));
            cs.setString(7, Ruta);

            cs.execute();

            System.out.println("persona registrada");

        } catch (SQLException e) {
            System.out.println("no insertado correctamente, error SQL:  " + e.getMessage());
        } catch (Exception e) {
            System.out.println("no insertado correctamente, error: " + e.getMessage());
        }

    }

    public void limpiar() {
        tfnom.setText("");
        tfapellido.setText("");
        tfcedula.setText("");
        tftelefono.setText("");
        tfemail.setText("");
        tfid.setText("");
        fecha.setCalendar(null);
        Ruta = "C:/Users/alexa/OneDrive/Documentos/NetBeansProjects/Registro/contact.png";
        Image mImagen = new ImageIcon(Ruta).getImage();
        ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH));
        foto.setIcon(mIcono);
        warningCedula.setVisible(false);
        warningTelefono.setVisible(false);
        warningEmail.setVisible(false);
        warningVacio.setVisible(false);
        
    }

    public void mostrarPersonas() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Cédula");
        modelo.addColumn("F. Nacimiento");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Email");
        modelo.addColumn("Foto de Perfil");

        TableRowSorter<TableModel> ordenarTabla = new TableRowSorter<TableModel>(modelo);

        tabla.setModel(modelo);
        tabla.setRowSorter(ordenarTabla);
        String sql = "select * from persona";

        String[] datos = new String[8];

        try {
            Statement st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);

                modelo.addRow(datos);
            }

            tabla.setModel(modelo);
            
            fecha.setCalendar(null);
            warningCedula.setVisible(false);
            warningTelefono.setVisible(false);
            warningEmail.setVisible(false);
            warningVacio.setVisible(false);
        } catch (Exception e) {
            System.out.println("No se pudo mostrar personas, " + e.toString());
        }
    }

    public void modificarPersona() {

        LocalDate dia = fecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {

            String consulta = "update persona set nom=?,ap=?,ced=?,tel=?,email=?,fecha=?,foto=? where id=? ";

//            int fila = tabla.rowAtPoint(e.getPoint());
//            String id = (String) tabla.getValueAt(fila, 0);
            String id = tfid.getText();

            CallableStatement cs = cn.prepareCall(consulta);

            cs.setString(1, tfnom.getText());
            cs.setString(2, tfapellido.getText());
            cs.setInt(3, Integer.parseInt(tfcedula.getText()));
            cs.setString(4, tftelefono.getText());
            cs.setString(5, tfemail.getText());
            cs.setDate(6, java.sql.Date.valueOf(dia));
            cs.setString(7, Ruta);

            cs.setString(8, id);

            cs.execute();

            System.out.println("persona " + id + " editada");

        } catch (SQLException e) {
            System.out.println("persona " + id + " no editada correctamente, error SQL:  " + e.getMessage());
        } catch (Exception e) {
            System.out.println("persona " + id + " no editada correctamente, error: " + e.getMessage());
        }

    }

    public void eliminar() {
        int fila = tabla.getSelectedRow();
//            String id = (String) tabla.getValueAt(fila, 0);
        String id = tfid.getText();

        try {
            String SQL = "delete from persona where id=" + tabla.getValueAt(fila, 0);
            Statement st = cn.createStatement();
            int n = st.executeUpdate(SQL);
            if (n >= 0) {
                System.out.println("Registro " + id + " eliminado");
            }
        } catch (Exception e) {
            System.out.println("Registro " + id + " no se pudo eliminar, error: " + e.getMessage());
        }

    }

    public boolean validarEmail(String email) {
        if(email.isEmpty()){
            return false;
        }else{
            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
        }
        
    }

    public boolean validarCedula(String cedula) {
        ArrayList<String> listaCedulas = new ArrayList<>();
        for (int i = 0; i < tabla.getRowCount(); i++) {
            listaCedulas.add(tabla.getValueAt(i, 3).toString());
        }
        
        if(cedula.isEmpty() || listaCedulas.contains(cedula)){
            return false;
        }else{
            int numCed = Integer.parseInt(cedula);
            if (numCed > 3000000 && numCed < 40000000) {
            return true;
        } else {
            return false;
        }
        }
        

    }

    public boolean validarTelefono(String telefono) {
        ArrayList<String> codigos = new ArrayList<>();
        codigos.add("0212"); // Caracas
        codigos.add("0234"); // Mérida
        codigos.add("0235"); // Barinas
        codigos.add("0238"); // San Carlos
        codigos.add("0239"); // San Cristóbal
        codigos.add("0241"); // Valencia
        codigos.add("0242"); // Maracay
        codigos.add("0243"); // La Victoria
        codigos.add("0244"); // Guacara
        codigos.add("0245"); // Puerto Cabello
        codigos.add("0246"); // San Diego
        codigos.add("0247"); // Guarenas
        codigos.add("0248"); // Los Teques
        codigos.add("0251"); // Barquisimeto
        codigos.add("0252"); // Acarigua
        codigos.add("0253"); // Araure
        codigos.add("0254"); // Carora
        codigos.add("0255"); // Cabudare
        codigos.add("0256"); // Yaritagua
        codigos.add("0257"); // Quíbor
        codigos.add("0258"); // El Tocuyo
        codigos.add("0259"); // Barquisimeto (Ejido)
        codigos.add("0261"); // Maracaibo
        codigos.add("0262"); // Cabimas
        codigos.add("0263"); // Ciudad Ojeda
        codigos.add("0264"); // Machiques
        codigos.add("0265"); // Santa Bárbara del Zulia
        codigos.add("0266"); // San Francisco
        codigos.add("0267"); // La Villa del Rosario
        codigos.add("0268"); // Lagunillas
        codigos.add("0269"); // San Carlos del Zulia
        codigos.add("0270"); // Tía Juana
        codigos.add("0271"); // Punto Fijo
        codigos.add("0272"); // Coro
        codigos.add("0273"); // Santa Ana de Coro
        codigos.add("0274"); // Puerto Cumarebo
        codigos.add("0275"); // Chichiriviche
        codigos.add("0276"); // Barquisimeto
        codigos.add("0277"); // Quibor
        codigos.add("0278"); // Carora (Siquisique)
        codigos.add("0279"); // El Tocuyo
        codigos.add("0281"); // Puerto La Cruz
        codigos.add("0282"); // Barcelona
        codigos.add("0283"); // Anaco
        codigos.add("0284"); // Cantaura
        codigos.add("0285"); // El Tigre
        codigos.add("0286"); // Santa Fe
        codigos.add("0287"); // Guanipa
        codigos.add("0288"); // Clarines
        codigos.add("0289"); // San Mateo
        codigos.add("0291"); // Maracay
        codigos.add("0292"); // La Victoria
        codigos.add("0293"); // Turmero
        codigos.add("0294"); // Cagua
        codigos.add("0295"); // San Juan de los Morros
        codigos.add("0296"); // San Fernando de Apure
        codigos.add("0297"); // Calabozo
        codigos.add("0298"); // Valle de la Pascua
        codigos.add("0299"); // Zaraza
        codigos.add("0412"); // Móviles Movistar
        codigos.add("0414"); // Móviles Movilnet
        codigos.add("0416"); // Móviles Movistar
        codigos.add("0424"); // Móviles Movilnet
        codigos.add("0426"); // Móviles Movistar
        codigos.add("0412"); // Móviles Digitel
        codigos.add("0424"); // Móviles Digitel
        codigos.add("0426"); // Móviles Digitel

        if(telefono.isEmpty() || telefono.length()<5){
            return false;
        }else{
            if (codigos.contains(telefono.substring(0, 4)) && telefono.length()==11){
        return true;
        }else{
            return false;
        }
        }
        
    }

    public boolean verificarEntradas(String email, String telefono, String cedula) {
        if(validarEmail(email) && validarTelefono(telefono) && validarCedula(cedula)){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        Ventana ven = new Ventana();
        ven.setLocationRelativeTo(null);
        ven.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guardar) {
            
            try {
                if(verificarEntradas(tfemail.getText(), tftelefono.getText(), tfcedula.getText()) && !tfnom.getText().isEmpty() && !tfapellido.getText().isEmpty()
                    && fecha.getCalendar() != null){
                insertarPersona();
                limpiar();
                mostrarPersonas();
            }else{
                if(validarEmail(tfemail.getText())){
                warningEmail.setVisible(false);
            }else{
                warningEmail.setVisible(true);
            }
            
            if(validarTelefono(tftelefono.getText())){
                      warningTelefono.setVisible(false);
                  }else{
                      warningTelefono.setVisible(true);
                  }
            
            if(validarCedula(tfcedula.getText())){
                warningCedula.setVisible(false);
            }else{
                warningCedula.setVisible(true);
            }
            
            if(tfnom.getText().isEmpty() || tfapellido.getText().isEmpty()
                    || fecha.getCalendar() == null || tftelefono.getText().isEmpty() || tfemail.getText().isEmpty() || tfcedula.getText().isEmpty()){
                warningVacio.setVisible(true);
            }else{
                warningVacio.setVisible(false);
            }
            }
            } catch (Exception er) {
                warningVacio.setVisible(true);
            }
                 
        }

        if (e.getSource() == nuevo) {
            limpiar();
        }

        if (e.getSource() == editar) {
            try {
                if(verificarEntradas(tfemail.getText(), tftelefono.getText(), tfcedula.getText()) && !tfnom.getText().isEmpty() && !tfapellido.getText().isEmpty()
                    && fecha.getCalendar() != null){
                modificarPersona();
                limpiar();
                mostrarPersonas();
            }else{
                if(validarEmail(tfemail.getText())){
                warningEmail.setVisible(false);
            }else{
                warningEmail.setVisible(true);
            }
            
            if(validarTelefono(tftelefono.getText())){
                      warningTelefono.setVisible(false);
                  }else{
                      warningTelefono.setVisible(true);
                  }
            
            if(validarCedula(tfcedula.getText())){
                warningCedula.setVisible(false);
            }else{
                warningCedula.setVisible(true);
            }
            
            if(tfnom.getText().isEmpty() || tfapellido.getText().isEmpty()
                    || fecha.getCalendar() == null || tftelefono.getText().isEmpty() || tfemail.getText().isEmpty() || tfcedula.getText().isEmpty()){
                warningVacio.setVisible(true);
            }else{
                warningVacio.setVisible(false);
            }
            }
            } catch (Exception er) {
                warningVacio.setVisible(true);
            }
            
        }

        if (e.getSource() == borrar) {
            eliminar();
            mostrarPersonas();
            limpiar();
        }

        if (e.getSource() == select) {

            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
            jFileChooser.setFileFilter(fileNameExtensionFilter);

            if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Ruta = jFileChooser.getSelectedFile().getAbsolutePath();
                Image mImagen = new ImageIcon(Ruta).getImage();
                ImageIcon icon = new ImageIcon(Ruta);
                ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH));
                foto.setIcon(mIcono);
            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        if (e.getSource() == tfnom || e.getSource() == tfapellido) {

            if (!Character.isAlphabetic(key)) {
                e.consume();
            }
        }

        if (e.getSource() == tfcedula || e.getSource() == tftelefono) {
            if (key < '0' || key > '9') {
                e.consume();
            }
        }

        if (e.getSource() == tfemail) {

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
