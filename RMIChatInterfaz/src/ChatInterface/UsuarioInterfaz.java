package ChatInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface UsuarioInterfaz extends Remote {
	
	public boolean enviarMensajePublico(String msg, String hora) throws RemoteException;
	public boolean enviarMensajePrivado(String msg, String hora, String receptor) throws RemoteException;
	public boolean cerrarSesion() throws RemoteException;
	public boolean iniciarSesion(String user) throws RemoteException;
	public boolean recibirMensajePrivado(String user, String msg) throws RemoteException;
	public boolean recibirMensajePublico(String msg) throws RemoteException;
	public String getIP() throws RemoteException;
	public String getUser()throws RemoteException;
	public String getPuerto() throws RemoteException;
	public boolean generarChat(String usuario) throws RemoteException;
	public boolean eliminarChat(String usuario) throws RemoteException;
	public void recibirUsuarios(ArrayList<String> usuarios) throws RemoteException;
	
}