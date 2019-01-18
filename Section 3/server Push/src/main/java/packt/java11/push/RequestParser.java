package packt.java11.push;

import javax.servlet.http.HttpServletRequest;

public class RequestParser {
    private final HttpServletRequest req;

    public RequestParser(HttpServletRequest req) {
        this.req = req;
    }

    static RequestParser parse(HttpServletRequest req){
        return new RequestParser(req);
    }

    long get(String name, long defaultValue){
        var s = req.getParameter(name);
        if( s == null ){
            return defaultValue;
        }
        return Long.parseLong(s);
    }
}
