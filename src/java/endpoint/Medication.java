/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

/**
 *
 * @author student
 */
public class Medication extends AbstractEndpoint {
    
    @Override
    protected String columnName() {
        return "Medication";
    }
}
