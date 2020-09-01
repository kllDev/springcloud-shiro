package com.kll.springcloud.filter;

import com.kll.springcloud.service.RedisService;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 全局自定义过滤器
 *
 * @author zzyy
 * @version 1.0
 * @create 2020/03/06
 */
@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisService redisService;

    private static String str;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info("come in global filter: {}", new Date());
//
        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getPath());
        System.out.println(request.getBody().toString());
        System.out.println(request.getHeaders().toString());
        System.out.println(request.getCookies().toString());
        String cookies = request.getCookies().toString().substring(1);
        String[] split = cookies.split("; ");

//        System.out.println(request.getCookies().toString());
//        String token = "";
//        String key = "";
//        String cookies = request.getCookies().toString();
//        String[] split = cookies.split("; ");
//        for (String s :
//                split) {
//            final String[] arr = s.split("=");
//            if ("PREFIX_USER_TOKEN_" == arr[0].substring(0,18)) {
//                token = arr[1];
//                key = arr[0];
//            }
//        }
//        String sysToken = redisService.get("PREFIX_USER_TOKEN_", key.substring(18));
//        if (!token.equals(sysToken)) {
//            log.info("token异常");
//            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            return exchange.getResponse().setComplete();
//        }
        //放行
        return chain.filter(exchange);
    }


    /**
     * 过滤器加载的顺序 越小,优先级别越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    public static void main(String[] args) throws Exception {
        //创建文件的输入流
        File file = new File("/Users/mac/Desktop/123.txt");
        FileInputStream fileInputstream = new FileInputStream(file);
        //通过filelnputstream获取对应的Filechannel->实际类型FilechannelImpl
        FileChannel filechannel = fileInputstream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //将通道的数据读入到Buffer
        filechannel.read(byteBuffer);
        byteBuffer.flip();
        //将byteBuffer的宇节数据转成string
        System.out.println(new String(byteBuffer.toString()));
        fileInputstream.close();

        String Gangway = "123";
    }
}
