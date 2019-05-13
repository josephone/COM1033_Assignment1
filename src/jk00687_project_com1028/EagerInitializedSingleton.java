package jk00687_project_com1028;

/**
 * @author Joseph Kutler
 *
 */
public class EagerInitializedSingleton {
	
	/*
	 * 
	 * This class is my attempt to create Singletons which is used so that the
	 * program could be better managed by reducing multiple instances of the same
	 * class. Due to time constraints I have not been able to fully implement
	 * this code, but if this program was to be revisited the inclusion of 
	 * this class would make it easier to proceed further. 
	 * 
	 */
	private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();

	private EagerInitializedSingleton() {

	}

	public static EagerInitializedSingleton getInstance() {
		return instance;
	}

}
