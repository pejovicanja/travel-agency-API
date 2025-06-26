package rs.ac.bg.fon.travel_agency.auth.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import rs.ac.bg.fon.travel_agency.auth.handlers.XSSRequestWrapper;

import java.io.IOException;

@WebFilter("/*")
public class XSSFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(
                new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }
}
