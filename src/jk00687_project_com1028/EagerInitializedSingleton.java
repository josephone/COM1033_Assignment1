package jk00687_project_com1028;

public class EagerInitializedSingleton {
	
	private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();
	
	private EagerInitializedSingleton() {
		
	}
	
	public static EagerInitializedSingleton getInstance() {
		return instance;
	}

}
