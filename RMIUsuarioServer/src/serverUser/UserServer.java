package serverUser;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;

import ChatInterface.ServidorPrincipal;
import ChatInterface.UsuarioInterfaz;
import controller.pantallaController;
import javafx.event.ActionEvent;
import model.Fachada;

public class UserServer extends UnicastRemoteObject implements UsuarioInterfaz{
	
	private Fachada fachada;
	
	public UserServer() throws RemoteException {
	}
	
	private ServidorPrincipal serverPrincipal;
	private static final long serialVersionUID = 1L;
	private final int PUERTO = 8886;
	private final int PUERTOs = 2369;
	
	private String user;
	
	@Override
	public boolean cerrarSesion() throws RemoteException {
		try {
			return serverPrincipal.apagarUsuario(this.user, this.getIp(), "" + PUERTO);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean iniciarSesion(String user) throws RemoteException {
		this.user = user;
		try {
			return serverPrincipal.inicarUsuario(this.user, this.getIp(), "" + PUERTO);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void iniciarServidor() {
		try {
			String dirIP = (InetAddress.getLocalHost()).toString();
			System.out.println("Escuchando en... " + dirIP + ":" + PUERTO);
			Registry registry = LocateRegistry.createRegistry(PUERTO);
			registry.bind("usuario", (UserServer) this);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getIp() throws RemoteException, UnknownHostException {
		String ip = (InetAddress.getLocalHost()).toString();
		String ipF[] = ip.split("/");
		return ipF[1];
	}

	public void localizarServidor() throws RemoteException{
		Registry registry = LocateRegistry.getRegistry("127.0.1.1", PUERTOs);
		ServidorPrincipal sPrincipal = null;
		try {
			sPrincipal = (ServidorPrincipal) registry.lookup("serverchat");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverPrincipal = (ServidorPrincipal) sPrincipal;
	}

	public String getUser() {
		return user;
	}

	@Override
	public boolean recibirMensajePrivado(String envio, String msg) throws RemoteException {
		fachada.imprimirPrivado(envio, msg);
		return false;
	}

	@Override
	public boolean recibirMensajePublico(String msg) throws RemoteException{
		fachada.imprimirPublico(msg);
		return false;
	}

	@Override
	public boolean enviarMensajePrivado(String msg, String hora, String receptor) throws RemoteException {
		return serverPrincipal.enviarMensajePrivado(this.user, msg, hora, receptor);
	}

	@Override
	public boolean enviarMensajePublico(String msg, String hora) throws RemoteException {
		return serverPrincipal.propagarMensajePublico(msg, this.user, hora);
	}

	@Override
	public String getIP() throws RemoteException {
		String ip = "";
		try {
			ip = (InetAddress.getLocalHost()).toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}

	@Override
	public String getPuerto() throws RemoteException {
		return "" + PUERTO;
	}

	public void setFacha(Fachada d) {
		this.fachada = d;
	}

	@Override
	public boolean eliminarChat(String arg0) throws RemoteException {
		fachada.eliminarChat(arg0);
		return true;
	}
	
	@Override
	public boolean generarChat(String arg0) throws RemoteException {
		return fachada.agregarChat(arg0);
	}

	@Override
	public void recibirUsuarios(ArrayList<String> usuarios) throws RemoteException {
		fachada.setUsuarios(usuarios);
	}
	
	public void llamarUsuarios() throws RemoteException {
		serverPrincipal.devolverUsuarios(user);
	}
}