import com.kll.springcloud.elasticsearch.MyEsRepository;
import com.kll.springcloud.entities.ArticleDetail;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private MyEsRepository itemRepository;

    /**
     * 创建索引
     */
    @Test
    public void createIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(ArticleDetail.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(ArticleDetail.class);
    }

//    /**
//     * 删除索引
//     */
//    @Test
//    public void deleteIndex() {
//        elasticsearchTemplate.deleteIndex("item");
//    }
//
//    /**
//     * 新增
//     */
//    @Test
//    public void insert() {
//        Item item = new Item(1L, "小米手机7", "手机", "小米", 2999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg");
//        itemRepository.save(item);
//    }
//
//    /**
//     * 批量新增
//     */
//    @Test
//    public void insertList() {
//        List<Item> list = new ArrayList<>();
//        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        list.add(new Item(3L, "华为META20", "手机", "华为", 4999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        list.add(new Item(4L, "iPhone X", "手机", "iPhone", 5100.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        list.add(new Item(5L, "iPhone XS", "手机", "iPhone", 5999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
//        // 接收对象集合，实现批量新增
//        itemRepository.saveAll(list);
//    }

}
