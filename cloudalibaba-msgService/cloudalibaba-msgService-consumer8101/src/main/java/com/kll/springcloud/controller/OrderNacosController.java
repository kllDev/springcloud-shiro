package com.kll.springcloud.controller;


import com.alibaba.fastjson.JSONObject;
import com.kll.springcloud.entities.ArticleDetail;
import com.kll.springcloud.entities.CommonResult;
import com.kll.springcloud.entities.Msg;
import com.kll.springcloud.entities.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class OrderNacosController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @GetMapping("/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serverUrl + "/payment/nacos/" + id, String.class);
    }

    @RequestMapping(value = "/nacos/getMsg", method = RequestMethod.GET)
    public CommonResult<JSONObject> getMsg(HttpServletRequest request) {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);

        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        return restTemplate.getForObject(serverUrl + "/getMsg?offset="+offset+"&limit="+limit, CommonResult.class);

    }

    @RequestMapping(value = "/nacos/addMsg", method = RequestMethod.GET)
    public CommonResult<JSONObject> addMsg(HttpServletRequest request) {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);

        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        return restTemplate.getForObject(serverUrl + "/addMsg?offset="+offset+"&limit="+limit, CommonResult.class);

    }

    @RequestMapping(value = "/nacos/getArticle", method = RequestMethod.GET)
    public CommonResult<JSONObject> getArticle(HttpServletRequest request) {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);
        String offset = request.getParameter("offset");
        String key = request.getParameter("key");
        String limit = request.getParameter("limit");
        return restTemplate.getForObject(serverUrl + "/getArticle?offset="+offset+"&limit="+limit+"&key="+key, CommonResult.class);

    }


    @RequestMapping(value = "/nacos/findListByContent", method = RequestMethod.GET)
    public CommonResult<JSONObject> findListByContent(HttpServletRequest request) {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);
        String content = request.getParameter("key");
        return restTemplate.getForObject(serverUrl + "/getArticle?key="+content, CommonResult.class);

    }

    @RequestMapping(value = "/nacos/getArticleDetail", method = RequestMethod.GET)
    public CommonResult<JSONObject> getArticleDetail(HttpServletRequest request) {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);

        String articleId = request.getParameter("articleId");
        String author = request.getParameter("author");
        return restTemplate.getForObject(serverUrl + "/getArticleDetail?articleId="+articleId+"&author="+author, CommonResult.class);

    }

    @RequestMapping(value = "/nacos/publishArticle", method = RequestMethod.POST)
    public CommonResult<JSONObject> login(@RequestBody ArticleDetail articleDetail) throws Exception {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);
        return restTemplate.postForObject(serverUrl + "/publishArticle", articleDetail, CommonResult.class);
    }

    @RequestMapping(value = "/nacos/getArticleByAuthor", method = RequestMethod.GET)
    public CommonResult<JSONObject> getArticleByAuthor(HttpServletRequest request) {
//        return restTemplate.getForObject(serverUrl + "/nacos/" , String.class);

        String offset = request.getParameter("offset");
        String author = request.getParameter("author");
        String limit = request.getParameter("limit");
        return restTemplate.getForObject(serverUrl + "/getArticleByAuthor?offset="+offset+"&author="+author+"&limit="+limit, CommonResult.class);

    }



}
