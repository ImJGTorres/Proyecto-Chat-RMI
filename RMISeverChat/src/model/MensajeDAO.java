package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MensajeDAO {

private Connection conn;
	
	public MensajeDAO() {
		try {
			conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		}catch(SQLException e) {
			System.err.println("No se pudo establecer la conexion: " + e.getMessage());
		}
	}
	
	public MensajeDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void revisarConexion() {
		if(this.conn == null) {
			try {
				conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			}catch(SQLException e) {
				System.err.println("No se pudo establecer la conexion: " + e.getMessage());
			}
		}
	}
	
	public boolean enviarMensaje(String info, String usuario, String fecha) {
		boolean exito = false;
		try {
			this.revisarConexion();
			Statement statementOb = conn.createStatement();			
			String sqlString = "INSERT INTO MENSAJES (INFO, USUARIO, FECHA) VALUES " + "('" + info + "', '" + usuario + "', + '" + fecha + "')";
			statementOb.execute(sqlString);
			exito = true;
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return exito;
	}
	
	public ArrayList<MensajeDTO> historial(){
		ArrayList<MensajeDTO> mensajes = new ArrayList<>();
		Statement statementOb = null;
		this.revisarConexion();
		try {
			statementOb = conn.createStatement();
			ResultSet rs = statementOb.executeQuery("SELECT * FROM MENSAJES");
			while(rs.next()) {
				MensajeDTO dto = new MensajeDTO();
				dto.setMsg(rs.getString("INFO"));
				dto.setUser(rs.getString("USUARIO"));
				String envio = rs.getString("FECHA");
				String[] fecha = envio.split("/");
				int year = Integer.parseInt(fecha[0]);
				int month = Integer.parseInt(fecha[1]);
				int day = Integer.parseInt(fecha[2]);
//				String hora = 
//				Date fechaF = new Date(year, month, day);
//				dto.setFecha(fechaF);
//				mensajes.add(dto);
			}
		}catch(Exception e) {
			System.err.println("Se presentó un error ejecutando la consulta. "+e.getMessage());
		}finally {
			try {
				statementOb.close();
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return mensajes;
	}
	
	
	/*
	public ArrayList<MensajeDTO> historialUsuario(String nombreUsuario){
		UsuarioDAO dao = new UsuarioDAO();
		ArrayList<MensajeDTO> mensajes = new ArrayList<>();
		String fechaRegistro = dao.getFechaRegistro(nombreUsuario);
		String[] fecha = fechaRegistro.split("-"); 
		if(fechaRegistro == "") {
			throw new RuntimeException("Usuario: " + nombreUsuario + " no registrado");
		}
		try {
			Statement statementOb = conn.createStatement();
			ResultSet rs = statementOb.executeQuery("SELECT * FROM MENSAJES");
			while(rs.next()) {
				String[] fechaMensaje = rs.getString("FECHA").split("-");
				if(this.compararFechas(fecha, fechaMensaje)) {
					MensajeDTO dto = new MensajeDTO();
					dto.setInfo(rs.getString("INFO"));
					dto.setUsuario(rs.getString("USUARIO"));
					dto.setFecha(rs.getString("FECHA"));
					mensajes.add(dto);
				}
			}
			return mensajes;
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	*/
	/**
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return true si fecha 1 es anterior a fecha2
	 */
	public boolean compararFechas(String[] fecha1, String[] fecha2) {
		return fecha1[0].compareTo(fecha2[0]) <= 0 && 
				fecha1[1].compareTo(fecha2[1]) <= 0 &&
				fecha1[2].compareTo(fecha2[2]) <= 0 &&
				fecha1[3].compareTo(fecha2[3]) <= 0 &&
				fecha1[4].compareTo(fecha2[4]) <= 0 &&
				fecha1[5].compareTo(fecha2[5]) <= 0;
	}
	
}