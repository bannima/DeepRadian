package main.component.exception;

/**
 * Created by Barry on 18/10/11.
 */
public class MissMatchDimensionException extends RuntimeException {
    public MissMatchDimensionException(String errorInfo){
        super(errorInfo);
    }
}
