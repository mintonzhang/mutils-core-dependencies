package cn.minsin.core.exception;

/**
 * 框架内发生致命异常时抛出
 *
 * @author minsin
 */
public class MutilsErrorException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 6614013909409203011L;

    public MutilsErrorException(String msg) {
        super(msg);
    }


    public MutilsErrorException(Throwable cause, String msg) {
        super(msg, cause);
    }

    public MutilsErrorException(Throwable cause) {
        super(cause);
    }

}
