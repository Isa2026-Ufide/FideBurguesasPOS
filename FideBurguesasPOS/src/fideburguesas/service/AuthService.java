package fideburguesas.service;

import fideburguesas.model.Usuario;
import fideburguesas.repository.DataStore;

public class AuthService {

    public Usuario login(String username,String password) throws Exception{

        for(Usuario u:DataStore.usuarios){

            if(u.getUsername().equals(username)){

                if(!u.verificarPassword(password))
                    throw new Exception("Credenciales inválidas");

                if(!u.isActivo())
                    throw new Exception("Usuario inactivo");

                return u;
            }
        }

        throw new Exception("Usuario no encontrado");
    }
}
