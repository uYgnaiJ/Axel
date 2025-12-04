package omniaetern.axel.common.http;

import omniaetern.axel.model.constant.ErrorCode;

public class StandardResponse<T> {
    public int code;
    public String message;
    public T data;
}
