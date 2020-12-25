package interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sound.midi.Soundbank;
import java.util.Enumeration;

public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Enumeration<String> headerNames = null;
        if (requestAttributes != null) {
            headerNames = requestAttributes.getRequest().getHeaderNames();
            while (headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                String header = requestAttributes.getRequest().getHeader(name);
                System.out.println(header + " : " + name);
                requestTemplate.header(name, header);
            }
        }

    }
}
