package com.totalplay.app.Batch.models;

public class Registro {	
	private String nombre;
	private String apeido;
	private String direccion;
	private Integer telefono;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApeido() {
		return apeido;
	}
	public void setApeido(String apeido) {
		this.apeido = apeido;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getTelefono() {
		return telefono;
	}
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	public Registro(String nombre, String apeido, String direccion, Integer telefono) {
		super();
		this.nombre = nombre;
		this.apeido = apeido;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	public Registro() {
		super();
	}
	
}
