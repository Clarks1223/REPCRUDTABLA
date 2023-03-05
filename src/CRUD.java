import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CRUD extends Ventanas {
    private JPanel mainPanel;
    private JButton JBeliminar;
    private JButton JBingresar;
    private JButton JBactualizar;
    private JButton JBbuscar;
    private JComboBox CBbuscar;
    private JRadioButton RBop1;
    private JRadioButton RBop2;
    private JTextField TFbuscar;
    private JLabel JLtitulo;
    private JLabel JLbuscar;
    private JLabel JLtitulo2;
    private JButton JBcancelar;
    private JButton JBaceptar;
    private JTextField TFid;
    private JTextField TFnom;
    private JTextField TFedad;
    private JTextField TFtelfono;
    private JTextField TFapellido;
    private JTextField TFcorreo;
    private JLabel JLid;
    private JLabel JLnombre;
    private JLabel JLtelefono;
    private JLabel JLapellido;
    private JLabel JLedad;
    private JLabel JLmail;
    //objeto tipo frame para correr la UGIF
    JFrame ventCRUD = new JFrame("CRUD");
    //Objeto de tipo conexion y varaibles
    Conexion conectarBD = new Conexion();
    Connection con;
    //Variable global prepared statement
    PreparedStatement ps;
    //Constructor
    public CRUD() {
        //Cargo las claves primarias al tbox
        try {
            con = conectarBD.estConexion();
            Statement consulta = con.createStatement();
            ResultSet resultado = consulta.executeQuery("select * from CAJERO");
            while (resultado.next()) {
                CBbuscar.addItem(resultado.getString(1));}
            con.close();} catch (HeadlessException | SQLException f) {
            System.err.println(f);}
        //desabilito un radio buttom
        RBop1.setSelected(true);
        TFbuscar.setEnabled(false);
        //Boton INGRESAR!!!!!!!!!!!!!
        JBingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado=0;//para comprobar los cambios
                try{
                    con=conectarBD.estConexion();
                    ps=con.prepareStatement("INSERT INTO CAJERO VALUES (?,?,?,?,?,?)");
                    ps.setString(1,TFid.getText());
                    ps.setString(2,TFnom.getText());
                    ps.setString(3,TFapellido.getText());
                    ps.setString(4,TFedad.getText());
                    ps.setString(5,TFcorreo.getText());
                    ps.setString(6, TFtelfono.getText());
                    resultado=ps.executeUpdate();
                    if(resultado>0){
                        JOptionPane.showMessageDialog(null,"PERSONA AGREGADA CORRECTAMENTE");
                    }else{
                        JOptionPane.showMessageDialog(null,"ERROR AL GUARDAR");
                    }
                    con.close();
                }catch (HeadlessException | SQLException f){
                    System.err.println(f);
                }
            }
        });
        //Accion del radio buttom 2 busqueda libre
        RBop2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TFbuscar.setEnabled(true);
                CBbuscar.setEnabled(false);
            }
        });
        //Accion del radio buttom 1 busqueda por clave primaria
        RBop1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TFbuscar.setEnabled(false);
                CBbuscar.setEnabled(true);
            }
        });
        //boton buscar!!!!!!!!!!!!
        JBbuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                if (TFbuscar.isEnabled()==true){
                    id=TFbuscar.getText();
                    botonBuscar(id);
                } else {
                    id=CBbuscar.getSelectedItem().toString();
                    botonBuscar(id);
                }
            }
        });
    }

    @Override
    public void abrirVentana(){
        ventCRUD.setContentPane(new CRUD().mainPanel);
        ventCRUD.setDefaultCloseOperation(ventCRUD.EXIT_ON_CLOSE);
        ventCRUD.pack();
        ventCRUD.setLocationRelativeTo(null);
        ventCRUD.setResizable(false);
        ventCRUD.setVisible(true);
    }
    @Override
    public void cerrarventana(){
        ventCRUD.setVisible(false);
    }

    public void botonBuscar(String id){
        int resultado=0;//para comprobar la busqueda
        try{
            con=conectarBD.estConexion();
            Statement consulta = con.createStatement();
            ResultSet resultadobus = consulta.executeQuery("select * from CAJERO where ID_CAJERO ="+id);
            while(resultadobus.next()){
                TFid.setText(resultadobus.getString(1));
                TFnom.setText(resultadobus.getString(2));
                TFapellido.setText(resultadobus.getString(3));
                TFedad.setText(resultadobus.getString(4));
                TFcorreo.setText(resultadobus.getString(5));
                TFtelfono.setText(resultadobus.getString(6));
                resultado++;
            }
            con.close();
            if (resultado<1){
                JOptionPane.showMessageDialog(null,"USUARIO NO ENCONTRADO");
            }
        }catch (HeadlessException | SQLException f){
            System.err.println(f);
        }
    }
}