package main.component.exception;

/**
 * Created by Barry on 18/10/11.
 */
public class WrongModelStructureException extends Exception {
    public WrongModelStructureException(String errorInfo){
        super(errorInfo);
    }
}
