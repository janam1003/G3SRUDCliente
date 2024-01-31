/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

/**
 *
 * @author danid
 */
import implementations.UserManagerImplementation;
import interfaces.UserManager;

/**
 *
 * @author danid
 */
public class UserManagerFactory {
    
    public static UserManager getUserManager(){
        
        return new UserManagerImplementation();
    
    }
    
}