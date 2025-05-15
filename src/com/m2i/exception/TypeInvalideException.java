package com.m2i.exception;

import com.m2i.compte.TypeCompte;

public class TypeInvalideException extends Exception {
	public TypeInvalideException(TypeCompte t)  {
		super();
		
		System.out.println("Le type "+t.name()+" est invalide");
	}

}
