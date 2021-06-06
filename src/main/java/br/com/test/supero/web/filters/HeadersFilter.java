package br.com.test.supero.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

public class HeadersFilter implements Filter {

    private static final String cacheControlValue = String.valueOf(Duration.ofHours(1).getSeconds());

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse responseHttp = (HttpServletResponse) response;
        HttpServletRequest requestHttp = (HttpServletRequest) request;

        responseHttp.setHeader("Access-Control-Allow-Origin", requestHttp.getHeader("Origin"));
        responseHttp.setHeader("Access-Control-Expose-Headers", "ETag, Renew-Token");

        if ("OPTIONS".equalsIgnoreCase(requestHttp.getMethod())) {
            responseHttp.setHeader("Access-Control-Allow-Methods", requestHttp.getHeader("Access-Control-Request-Method"));
            responseHttp.setHeader("Access-Control-Allow-Headers", requestHttp.getHeader("Access-Control-Request-Headers"));
            responseHttp.setHeader("Access-Control-Max-Age", cacheControlValue);
        } else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }


}
