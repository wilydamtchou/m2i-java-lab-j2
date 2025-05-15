package com.m2i.application;


import com.m2i.application.MonException;
import com.m2i.utils.IdUtil;

public class Test {
    public void method () throws MonException  {
        throw new MonException("Erreur personnalisée !");
    }
}

