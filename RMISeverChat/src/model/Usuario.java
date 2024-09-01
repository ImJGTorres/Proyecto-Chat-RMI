package model;

public class Usuario {

	String puerto;
	String ip;
	String user;
	public Usuario(String user, String ip, String puerto) {
		super();
		this.puerto = puerto;
		this.ip = ip;
		this.user = user;
	}
	public String getPuerto() {
		return puerto;
	}
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
