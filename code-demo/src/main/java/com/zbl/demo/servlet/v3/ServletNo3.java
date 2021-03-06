package com.zbl.servlet.v3;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zbl
 * @Date: Created in 9:37 2020/1/16
 * @Description: 责任链模式 4.0
 * @Version: $
 */
public class ServletNo3 {

    public static void main(String[] args) {
        Request request = new Request();
        request.setStr("request");
        Response response = new Response();
        response.setStr("response");
        FilterChain filterChain = new FilterChain();
        filterChain.add(new HTMLFilter()).add(new HeaderFilter());
        filterChain.doFilter(request, response);

    }

}


class Request {
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

class Response {
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

interface Filter {
    boolean doFilter(Request request, Response response, FilterChain filterChain);
}

class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    int index = 0;

    public FilterChain add(Filter f) {
        filters.add(f);
        return this;
    }


    public boolean doFilter (Request request, Response response){
        if (index == filters.size()) return false;
        Filter f = filters.get(index);
        index++;
        return f.doFilter(request, response ,this);
    }
}

class HTMLFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, FilterChain filterChain) {
        System.out.println("Html request");
        filterChain.doFilter(request, response);
        System.out.println("Html response");
        return true;
    }
}

class HeaderFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, FilterChain filterChain) {
        System.out.println("Header request");
        filterChain.doFilter(request, response);
        System.out.println("Header response");
        return true;
    }
}

