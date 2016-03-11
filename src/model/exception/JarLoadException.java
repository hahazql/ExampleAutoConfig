package model.exception;


import com.hahazql.util.exception.BaseException;

/**
 * Created by zql on 2015/10/13.
 */
public class JarLoadException extends BaseException
{

    public JarLoadException() {
        super();
    }

    public JarLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public JarLoadException(String message) {
        super(message);
    }

    public JarLoadException(Throwable cause) {
        super(cause);
    }
}
