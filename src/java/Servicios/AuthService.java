/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.AuthDAO;

import java.util.Map;

public class AuthService {
    
    private final AuthDAO AuthDAO;
    
    public AuthService() {
        this.AuthDAO = new AuthDAO();
    }
    
    public Map<String, Object> authenticate(String username, String password) {  
        return AuthDAO.authenticate(username, password);
    }
}
