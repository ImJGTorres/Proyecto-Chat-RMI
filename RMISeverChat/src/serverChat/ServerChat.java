package serverChat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;

import ChatInterface.ServidorPrincipal;
import model.MensajeDAO;

public class ServerChat extends UnicastRemoteObject implements ServidorPrincipal {

	private static volatile ServerChat instance;
	private MensajeDAO mensaje;
	
	protected ServerChat() throws RemoteException {
		notificador = new ObserverServidores();
		mensaje = new MensajeDAO();
	}

	public static ServerChat getInstance() throws RemoteException {
		ServerChat result = instance;
		if (result != null) {
			return result;
		}
		// ...
		synchronized (ServerChat.class) {
			// ...
			if (instance == null) {
				instance = new ServerChat();
			}
			return instance;
		}
	}

	
	private ObserverServidores notificador;
	
	private static final long serialVersionUID = 1L;
	private final int PUERTO = 2369;
	
	public static void main(String [] args) throws RemoteException {
		(new ServerChat()).iniciarServidor();
	}
	
	@Override
	public boolean inicarUsuario(String user, String ip, String puerto) throws RemoteException {
		try {
			return notificador.addObserver(user, ip, puerto);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean apagarUsuario(String user, String ip, String puerto) throws RemoteException {
		try {
			notificador.deleteObserver(user);
		} catch (RemoteException | UnknownHostException | NotBoundException e) {
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
			registry.bind("serverchat", (ServidorPrincipal) this);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean propagarMensajePublico(String msg, String user, String hora) throws RemoteException {
		mensaje.enviarMensaje(msg, user, hora);
		String h[] = hora.split("/");
		String hour = h[3];
		String minute = h[4];
		String mensajeArmado = user + "(" + hour + ":" + minute + ")" + ": " + msg;
		try {
			return notificador.propagarMensajePublico(user, mensajeArmado);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean enviarMensajePrivado(String user, String msg, String hora, String receptor) throws RemoteException {
		String h[] = hora.split("/");
		String hour = h[3];
		String minute = h[4];
		String mensajeArmado = user + "(" + hour + ":" + minute + ")" + ": " + msg;
		try {
			return notificador.enviarMensajePrivado(receptor, mensajeArmado, user);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void devolverUsuarios(String user) throws RemoteException {
		try {
			notificador.enviarUsuarios(user);
		} catch (NumberFormatException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}