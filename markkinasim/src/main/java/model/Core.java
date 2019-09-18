package model;

public class Core {
	
	//SINGLETON PATTERN//////////////////
	//mahdollistaa vain yhden Core-olion olemassaolon!
	private static Core core;
	private Core() {}
	public static Core getInstance() {
		if (core == null) {
			core = new Core();
		}
		return core;
	}
	////////////////////////////////////
	
	public void init() {
		
	}
	
	public void start() {
		
	}
}
