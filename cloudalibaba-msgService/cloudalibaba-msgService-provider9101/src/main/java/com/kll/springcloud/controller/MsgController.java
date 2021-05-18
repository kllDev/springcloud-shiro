package com.kll.springcloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kll.springcloud.VO.ArticleDetailVO;
import com.kll.springcloud.elasticsearch.MsgPOJO;
import com.kll.springcloud.elasticsearch.MyEsRepository;
import com.kll.springcloud.entities.*;

import com.kll.springcloud.service.EsService;
import com.kll.springcloud.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin("*")
public class MsgController {
    @Autowired
    private MsgService msgService;

    @Autowired
    private EsService esService;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//    @Autowired
//    private MyEsRepository repository;


//    @GetMapping("/user/login/{id}")
//    public String getPayment(@PathVariable("id") Integer id) {
//        redisService.set("payment","nacos",id.toString());
//        redisService.expire("payment", "nacos", 4000, TimeUnit.MILLISECONDS);
//        return "nacos register, serverport=" + serverPort + "\t id:" + id;
//    }


    /*
        es      controller
     */
    @RequestMapping("findAll")
    public Iterable<ArticleDetail> findAll() {

        return esService.findAll();
    }

    @RequestMapping("list")
    public String save() {
        List<ArticleDetail> list = null;
        esService.save(list);

        return "success";
    }

    @RequestMapping("save")
    public void save(ArticleDetail bean) {
        esService.save(bean);
    }

    @RequestMapping("findBySummary/{key}")
    public List<ArticleDetail> findBySummary(@PathVariable("key") String summary) {
        return esService.findListBySummary(summary);
    }

    @RequestMapping("findListByContent")
    public CommonResult<JSONObject> findListByContent(HttpServletRequest request) {
        String content = request.getParameter("key");
        String offset = "1";
        String limit = "4";
        int offsetInt = Integer.parseInt(offset);
        int limitInt = Integer.parseInt(limit);
        List<Article> articles = new ArrayList();
        if (content.length() >= 1) {
            List<ArticleDetail> articleDetails = esService.findListByContent(content);
            for (ArticleDetail a :
                    articleDetails) {
                articles.add(new Article(a.getId(), a.getArticleId(), a.getArticleTitle(), a.getAuthor(), a.getSummary(), a.getCreateTime(), a.getCategories()));
            }
            Integer count = articleDetails.size();
            String json = "";
            if (limitInt < articleDetails.size()) {
                json = JSONArray.toJSONString(articles.subList(offsetInt - 1, offsetInt + limitInt - 1));
            } else {
                json = JSONArray.toJSONString(articles);
            }
            JSONArray jsonArray = JSONObject.parseArray(json);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rows", jsonArray);
            jsonObject.put("total", count);
            CommonResult<JSONObject> result = new CommonResult<JSONObject>();
            result.setData(jsonObject);
            result.setCode(200);
            result.setMessage("查询成功");
            return result;
        }
        articles = msgService.getArticle(offsetInt - 1, offsetInt + limitInt - 1);
        Integer count = msgService.getCount();
        String json = JSONArray.toJSONString(articles);
        JSONArray jsonArray = JSONObject.parseArray(json);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", jsonArray);
        jsonObject.put("total", count);
        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }


    @GetMapping("/findByName/{key}")
    public CommonResult<JSONObject> getPayment(@PathVariable("key") String key) {
//        elasticsearchTemplate.createIndex(ArticleDetail.class);
//        elasticsearchTemplate.putMapping(ArticleDetail.class);
//        ArticleDetail articleDetail = new ArticleDetail();
//        articleDetail.setId(1);
//        articleDetail.setArticleTitle("articleDetailVO.getArticleTitle()");
//        articleDetail.setAuthor("articleDetailVO.getAuthor()");
//        articleDetail.setCategories("java");
//        articleDetail.setContent("这是内容，这是内容，这些都是内容，这是内容，这是内容，这些都是内容，这是内容，这是内容，这些都是内容，这是内容，这是内容，这些都是内容，这是内容，这是内容，这些都是内容");
//        articleDetail.setSummary("这是内容，这是内容，这些都是内容，");
//        articleDetail.setType("原创");
//        articleDetail.setArticleId("1231322131");
//        articleDetail.setVisits(String.valueOf(0));
//        articleDetail.setCreateTime((new Date()).toString());
//        repository.save(articleDetail);

//        Item item = new Item(1L, "小米手机7", "手机", "小米", 2999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg");
//        itemRepository.save(item);
//        List<Item> list = new ArrayList<>();
//        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        list.add(new Item(3L, "华为META20", "手机", "华为", 4999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        list.add(new Item(4L, "iPhone X", "手机", "iPhone", 5100.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        list.add(new Item(5L, "iPhone XS", "手机", "iPhone", 5999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        // 接收对象集合，实现批量新增
//        itemRepository.saveAll(list);
        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("rows", esService.findByName(key));
        jsonObject.put("total", 40);
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }


    @GetMapping("/getMsg")
    public CommonResult<JSONObject> getMsg(HttpServletRequest request) throws Exception {
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");


        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        JSONObject jsonObject = new JSONObject();
        ArrayList arrayList = new ArrayList();
        Date date = new Date();
        for (int i = 0; i < Integer.parseInt(limit); i++) {
            arrayList.add(new Msg(1, "aaa", date.toString(), "testsssssssssss"));
        }
        jsonObject.put("rows", arrayList);
        jsonObject.put("total", 40);
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }


    @GetMapping("/addMsg")
    public CommonResult<JSONObject> addMsg(HttpServletRequest request) throws Exception {
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");


        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        JSONObject jsonObject = new JSONObject();
        ArrayList arrayList = new ArrayList();
        Date date = new Date();
        for (int i = 0; i < Integer.parseInt(limit); i++) {
            arrayList.add(new Msg(1, "aaa", date.toString(), "testsssssssssss"));
        }
        jsonObject.put("rows", arrayList);
        jsonObject.put("total", 40);
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }

    @GetMapping("/getArticle")
    public CommonResult<JSONObject> getArticle(HttpServletRequest request) {
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        String key = request.getParameter("key");

        int offsetInt = Integer.parseInt(offset);
        int limitInt = Integer.parseInt(limit);
        List<Article> articles = null;
        if (key.length() >= 1) {
            articles = msgService.getArticleByKey(offsetInt - 1, offsetInt + limitInt - 1, key);
            Integer count = msgService.getCountByKey(key);
            String json = JSONArray.toJSONString(articles);
            JSONArray jsonArray = JSONObject.parseArray(json);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rows", jsonArray);
            jsonObject.put("total", count);
            CommonResult<JSONObject> result = new CommonResult<JSONObject>();
            result.setData(jsonObject);
            result.setCode(200);
            result.setMessage("查询成功");
            return result;
        }
        articles = msgService.getArticle(offsetInt - 1, offsetInt + limitInt - 1);
        Integer count = msgService.getCount();
        String json = JSONArray.toJSONString(articles);
        JSONArray jsonArray = JSONObject.parseArray(json);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", jsonArray);
        jsonObject.put("total", count);
        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }

    @GetMapping("/getArticleByAuthor")
    public CommonResult<JSONObject> getArticleByAuthor(HttpServletRequest request) {
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        String author = request.getParameter("author");
        int offsetInt = Integer.parseInt(offset);
        int limitInt = Integer.parseInt(limit);
        List<Article> articles = null;
        articles = msgService.getArticleByAuthor(offsetInt - 1, offsetInt + limitInt - 1, author);
        Integer count = msgService.getCount();
        String json = JSONArray.toJSONString(articles);
        JSONArray jsonArray = JSONObject.parseArray(json);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", jsonArray);
        jsonObject.put("total", count);
        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }

    @GetMapping("/getArticleDetail")
    public CommonResult<JSONObject> getArticleDetail(HttpServletRequest request) {
        String articleId = request.getParameter("articleId");
        ArticleDetail articleDetail = msgService.getArticleDetail(articleId);
        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        String json = JSONObject.toJSONString(articleDetail);
        JSONObject jsonObject = JSONObject.parseObject(json);
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }

    /*
            author: this.articleInfoForm.author,
            articleTitle: this.articleTitle,
            type: this.articleInfoForm.articleType,
            categories: categories,
            content: this.$refs.markdownContent.innerText,
            summary: summary
     */

    @RequestMapping(value = "/publishArticle", method = RequestMethod.POST)
    public CommonResult<JSONObject> publishArticle(@RequestBody ArticleDetailVO articleDetailVO) {
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setId(1);
        articleDetail.setArticleTitle(articleDetailVO.getArticleTitle());
        articleDetail.setAuthor(articleDetailVO.getAuthor());
        articleDetail.setCategories(articleDetailVO.getCategories());
        articleDetail.setContent(articleDetailVO.getContent());
        articleDetail.setSummary(articleDetailVO.getSummary());
        articleDetail.setType(articleDetailVO.getType());
        articleDetail.setArticleId(UUID.randomUUID().toString());
        articleDetail.setVisits(String.valueOf(0));
        articleDetail.setCreateTime((new Date()).toString());
        System.out.println(articleDetail.toString());
        Integer id=msgService.publishArticle(articleDetail);
        articleDetail.setId(id);
        esService.save(articleDetail);

        CommonResult<JSONObject> result = new CommonResult<JSONObject>();
        JSONObject jsonObject = new JSONObject();
        result.setData(jsonObject);
        result.setCode(200);
        result.setMessage("发布成功");
        return result;
    }

}
