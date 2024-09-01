package model;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import controller.pantallaController;
import serverUser.UserServer;

public class Fachada {
	private static volatile Fachada instance;
	private UserServer serverUser;
	private pantallaController pantalla = null;
	
	
	private Fachada() throws RemoteException {
		serverUser = new UserServer();
		serverUser.iniciarServidor();
		serverUser.localizarServidor();
		serverUser.setFacha(this);
	}
	
	public static Fachada getInstance() throws RemoteException {
		Fachada result = instance;
		if (result != null) {
			return result;
		}
		// ...
		synchronized (Fachada.class) {
			// ...
			if (instance == null) {
				instance = new Fachada();
			}
			return instance;
		}
	}

	public boolean iniciarSesion(String user) throws RemoteException {
		return serverUser.iniciarSesion(user);
	}
	
	public boolean cerrarSesion() throws RemoteException {
		return serverUser.cerrarSesion();
	}
	
	public boolean enviarMensajePublico(String msg, String hora) throws RemoteException {
		//this.subirMensajeBD(msg, hora);
		return serverUser.enviarMensajePublico(msg, hora);
	}
	
	/*
	private void subirMensajeBD(String mensaje, String hora) {
		MensajeDAO dao = new MensajeDAO();
		dao.enviarMensaje(mensaje, this.pantalla.getNombreUsuario(), hora);
	}
	*/
	
	public boolean enviarMensajePrivado(String user, String msg, String hora, String receptor) throws RemoteException {
		return serverUser.enviarMensajePrivado(msg, hora, receptor);
	}
	
	public void imprimirPublico(String msg) {
		pantalla.imprimirPublico(msg);
	}
	
	public void imprimirPrivado(String user, String msg) {
		pantalla.imprimirPrivado(user, msg);
	}
	
	public void setPantalla(pantallaController p) {
		this.pantalla = p;
	}
	
	public boolean agregarChat(String usuario) throws RemoteException {
		 pantalla.crearTabs(usuario);
		return true;
	}
	
	public void eliminarChat(String usuario) {
		pantalla.eliminarChat(usuario);
	}
	
	public void setUsuarios(ArrayList<String> usuarios) {
		pantalla.setUsuarios(usuarios);
	}
	
	public void llamarUsuarios() throws RemoteException {
		serverUser.llamarUsuarios();
	}
	
}