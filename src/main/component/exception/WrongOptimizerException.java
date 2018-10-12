package main.component.exception;

/**
 * Created by Barry on 18/10/11.
 */
public class WrongOptimizerException extends Exception {
    public WrongOptimizerException(String errorInfo){
        super(errorInfo);
    }
}
