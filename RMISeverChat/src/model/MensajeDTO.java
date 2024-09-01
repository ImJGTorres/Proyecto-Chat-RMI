package model;

import java.sql.Date;

public class MensajeDTO {
	
	String user;
	String msg;
	String fecha;
	
	public MensajeDTO() {
	}
	
	public MensajeDTO(String user, String msg, String fecha) {
		this.user = user;
		this.msg = msg;
		this.fecha = fecha;
		
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}