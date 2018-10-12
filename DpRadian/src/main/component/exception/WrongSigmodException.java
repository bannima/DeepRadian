package main.component.exception;

/**
 * Created by Barry on 18/10/11.
 */
public class WrongSigmodException extends RuntimeException {
    public WrongSigmodException(String errorInfo){
        super(errorInfo);
    }
}
