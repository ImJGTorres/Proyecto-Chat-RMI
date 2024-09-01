package ChatInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServidorPrincipal extends Remote {

	public boolean propagarMensajePublico(String msg, String user, String hora) throws RemoteException;
	public boolean inicarUsuario(String user, String ip, String puerto) throws RemoteException;
	public boolean apagarUsuario(String arg0, String ip, String user) throws RemoteException;
	public boolean enviarMensajePrivado(String user, String msg, String hora, String receptor) throws RemoteException;	
	public void devolverUsuarios(String user) throws RemoteException;
}