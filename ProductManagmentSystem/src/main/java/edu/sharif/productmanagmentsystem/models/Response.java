package edu.sharif.productmanagmentsystem.models;

import edu.sharif.productmanagmentsystem.Utils.Utils;

public class Response {
    private String correlationID;
    private String errorCode;
    private String responseBody;

    public Response(String errorCode, String responseBody) {
        this.correlationID = Utils.createCorrelationID();
        this.errorCode = errorCode;
        this.responseBody = responseBody;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
