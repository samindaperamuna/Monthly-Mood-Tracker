package org.fifthgen.colouringbooks.model.bean;

/**
 * Created by GameGFX Studio on 2015/9/21.
 */
@SuppressWarnings("unused")
public class ResponseBean {
    protected String errorCode;
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
