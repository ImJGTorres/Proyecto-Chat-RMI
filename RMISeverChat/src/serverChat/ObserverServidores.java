package serverChat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import ChatInterface.UsuarioInterfaz;
import model.Usuario;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ObserverServidores{
	protected ObserverServidores(){
		servidores = new ArrayList<Usuario>();
	}

	private List<Usuario> servidores;
	
	public boolean addObserver(String user, String ip, String puerto) throws RemoteException, NotBoundException{
		Usuario u = new Usuario(user, ip, puerto);
		for(Usuario usuario : servidores) {
			if(usuario.getUser().equals(user)) {
				return false;
			}
		}
		ArrayList<String> devolver = new ArrayList<String> ();
		for(Usuario usuario : servidores) {
			if(!usuario.getUser().equals(user))		
				devolver.add(usuario.getUser());
		}
		for(Usuario usuarios : servidores) {
			if(!usuarios.getUser().equals(user)){
				Registry registry = LocateRegistry.getRegistry(usuarios.getIp(), Integer.parseInt(usuarios.getPuerto()));
				UsuarioInterfaz sUser = (UsuarioInterfaz) registry.lookup("usuario");
				sUser.recibirUsuarios(devolver);
			}
		}
		servidores.add(u);
		return true;
	}
	
	public boolean propagarMensajePublico(String envio, String msg) throws RemoteException, NotBoundException {
		for (Usuario u : servidores) {
			if(!u.getUser().equals(envio)) {
				Registry registry = LocateRegistry.getRegistry(u.getIp(), Integer.parseInt(u.getPuerto()));
				UsuarioInterfaz sUser = (UsuarioInterfaz) registry.lookup("usuario");
				sUser.recibirMensajePublico(msg);
			}
		}
		return true;
	}
	
	public boolean deleteObserver(String user) throws RemoteException, UnknownHostException, NotBoundException {
		for(Usuario u : servidores) {
			if(u.getUser().equals(user)) {
				servidores.remove(u);
				return true;
			}
		}
		return true;
	}
	
	public boolean enviarMensajePrivado(String user, String msg, String envio) throws RemoteException, NotBoundException {
		for(Usuario u : servidores) {
			if(u.getUser().equals(user)) {
				Registry registry = LocateRegistry.getRegistry(u.getIp(), Integer.parseInt(u.getPuerto()));
				UsuarioInterfaz sUser = (UsuarioInterfaz) registry.lookup("usuario");
				System.out.println(user + " " + msg + " con esta ip: " + u.getIp() + " con este puerto" + u.getPuerto() );
				sUser.recibirMensajePrivado(envio, msg);
				return true;
			}
		}
		return false;
	}
	
	public void enviarUsuarios(String user) throws NumberFormatException, RemoteException, NotBoundException {
		ArrayList<String> devolver = new ArrayList<String> ();
		for(Usuario u : servidores) {
			System.out.println("********");
			System.out.println(user);
			System.out.println(u.getUser());
			System.out.println("********");
			if(!u.getUser().equals(user))		
				devolver.add(u.getUser());
		}
		for(Usuario u : servidores) {
			if(u.getUser().equals(user)) {
				Registry registry = LocateRegistry.getRegistry(u.getIp(), Integer.parseInt(u.getPuerto()));
				UsuarioInterfaz sUser = (UsuarioInterfaz) registry.lookup("usuario");
				sUser.recibirUsuarios(devolver);
				return;
			}
		}
	}
	
}