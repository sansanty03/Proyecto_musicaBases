/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author santi
 */
public class NegociosException extends Exception {

    public NegociosException() {
    }

    public NegociosException(String message) {
        super(message);
    }

    public NegociosException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegociosException(Throwable cause) {
        super(cause);
    }

    public NegociosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
