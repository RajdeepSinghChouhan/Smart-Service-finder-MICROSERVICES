package in.ssf.allServices.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String msg) {
        super(msg);
    }
}
