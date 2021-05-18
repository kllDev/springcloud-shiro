package com.kll.springcloud.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.kll.springcloud.model.OrderInfo;
import com.kll.springcloud.model.PayInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-29 14:11
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/pay")
public class PayController {
    @Value("${service-url.order-duration}")
    private String orderDurationServiceUrl;

    private final RestTemplate restTemplate;


    private final String APP_ID = "2021000117646564";
    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQ1dKcZNb19aAvRAm86SbIAQC5ZoD65131DdkvCuHUi3qDvjxmTBM2t8roKxoXtZiAieN/yduavr4oTlqUsW9mk+ji57e0KM3dtDc/InNDywCqQMu4urHE8EXrm3m96EUQmni1+FfP0NiRRXOXaDmqIW8ETX7Fju73nUhKDtWY6n7WWsSRJCMKLPCh+2SK/w9w52NAxr0+w6l8ubWmYm82yz2frZIE7LugkiugF7p0jPLIQRWUwGGN3SLFbw3t0qQtoEPTGjnIvAgmW9O4YWfwrxUWExs/KxRpN0KjqMu9BV2+EgdVBnNCZFQgqPGzoVY6PzpC5EYkWDw66jyXHQcZAgMBAAECggEARg+I36pP6Y61u+d3pnBKGDmT9htFjRlac+4M/xqOf1gxHYVqhjmKmc9yUbh32Lw5VwRcjjZBCqv2JF0yp/bk4oPuBcT9MiMC4poIl3u8sjCC09igM23sDE4GJKCC1YCD2DJA7hWBtL9b43nrHnSGYGvn3fb2ue4YXqMwktJ2NI+QEC7pRmn7kF0dtoCxRJRLkuFirxxEkio2YulT3XJppDzeMUAwOU9YMyqB0V0QZXQbbcG3ICfSety3vCG4Nv7tKm9UfUBWdEdhmuBrR0kbmmsKk5X0NIreMGnx349Wt+z6nyEILySqz5GdA943R07RqKNaJjbAVtXdPvNuGSB0eQKBgQDFBsdaV1TtcIwF/dC93hjySmRzS/BDb7hIeGXGQ/X69C/KB91Yxl4JNYmf+VA96fIZSfbmhWwKi44Vt6adF+MXwK2PuK/opwr1BePXnFKQ9tZdyjRc/PufS4uU1GeIEGGKXUTqau/HP/gdE2cxqrdAf3bUKlb6XMhitOV3yBBvNwKBgQC8L99wcvziBNybmrL+nsVTyzzhYn3Hx4c3Pi5Qhdr6i6bA2wc8cjnc47g4vIK0MQfbj8memHiThOAHP72Vu8uQa32Sx7wWug5P2apGoUNrFDcGvDEcp1Og46hE1gMdiHcqWN/J0bXykZORoJ6KhChFs2C09jmdx0kkgNQyMOJELwKBgF0KSnGa8/v8PeNGbKB8lQM1piHcJmVyCHAQit8JkMslLLwW4qvdYOKwaYEl1oKmSzEuAG3za4W/SSVwSDm+4fqBfnR0Hn2bBE2YrnNO2p6iMbCwn/GZdalPn2Z0tz9FnQod6UFZpklNJ/lzBDQsDcVja9Xc+J3Z2rbh/cqGq1wZAoGATfCp4wHM2LNYxx2IOsp5QZ4wJczL+V0bFHctaW425oPGWaOdWJyaeHDcEKuKmqSf84CW6ZeOgNKMkpQEqiowQi0ywgL1VJhUax/kVQJfUM77h0+UK6DlkwIW5AObILUvXZuXfBfd+ovuiWbzorzfjRag2/IKpN0v3u14dR0n6UMCgYAyPs/WWDFeF7Td1hAOSXNnTnNxsF2s4l7KHPIYvxt9CoNCBQ2kEdNpo/jYwtAqX80SKEVe/p0Dx+Tq0pC3XhVvjhIijuB56flXxAiXW/ChSY4E/YDWE7KYvJDwwvEkHeMfv3eSlDc8xf/A9zjpl/BzsW6th6VBR+U7pd7nFf6xQA==";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp8Sap30vQTawkF2wU7gDmn8gn6P/0CpXlVjzzSpHSMP5gTUhMpZNz1G7/VmG1wRcDiuyqP1Zat8m0ZX7a8I4XpsHMasAUcrYCVTNuCb3hFzxKGm4CTL96xM/khCkpa9VwcZWzOaNQpr87rCQofevL5JygM2pSUEi1fe79T61z/U/uyJKbu3jZdFFr5GRLBpNKxbWReAOjcwHyRcYIzRuAC3o0idazMStUge/YdIQF5yeif4JmAITwXqryn1uvR3uMarr1o1UkfzsWYpTPWU8Df2Am9yiyI8tHTmNw5I6oEOeUj0lDZpmOklwAhG1x+qJqmqSKTc5FxBjGJe5UDM/LwIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://localhost/mall/#/payDone";

    public PayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @PostMapping("/ali")
    public void alipay(@RequestBody PayInfo payInfo , HttpServletResponse httpResponse) throws IOException {
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL+"/"+payInfo.getOrderId());
        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount =Double.toString(payInfo.getPrice());
        //订单名称，必填
        String subject =payInfo.getTitle();
        //商品描述，可空
        String body = "";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }



    @PostMapping("/done")
    public String getProductInfo(@RequestBody String code) {
        Boolean res=restTemplate.postForObject(orderDurationServiceUrl + "/order/pay", Integer.valueOf(code), Boolean.class);
        return "ok";
    }
}