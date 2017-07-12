package example.com.sunshine.download.Http.entity;


import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseResponse<T> implements Serializable {

    StatusUnit status;

    public StatusUnit getStatus() {
        return status;
    }

    public void setStatus(StatusUnit status) {
        this.status = status;
    }
}
