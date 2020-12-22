package com.changgou.canal.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.changgou.item.feign.PageFeign;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.InsertListenPoint;
import com.xpand.starter.canal.annotation.ListenPoint;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

@CanalEventListener
public class CanalDataEventListener {

    @Autowired
    private ContentFeign contentFeign;

    @Autowired
    private PageFeign pageFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ListenPoint(destination = "example",
            schema = "changgou_content",
            table = {"tb_content", "tb_content_category"},
            eventType = {
                    CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE,
                    CanalEntry.EventType.INSERT})
    public void onDataChanged(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        String category_id = getCategoryId(eventType, rowData);
        System.out.println("category_id: " + category_id);
        if (category_id != null){
            List<Content> contentList = contentFeign.findByCategory(Long.valueOf(category_id)).getData();
            stringRedisTemplate.boundValueOps("content_" + category_id).set(JSON.toJSONString(contentList));
        }
    }

    private String getCategoryId(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        if (eventType == CanalEntry.EventType.DELETE){
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()){
                if (column.getName().equalsIgnoreCase("category_id")){
                    return column.getValue();
                }
            }
        }else {
            for (CanalEntry.Column column : rowData.getAfterColumnsList()){
                if (column.getName().equalsIgnoreCase("category_id")){
                    return column.getValue();
                }
            }
        }
        return null;
    }


    @ListenPoint(destination = "example",
            schema = "changgou_goods",
            table = {"tb_spu"},
            eventType = {
                    CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE,
                    CanalEntry.EventType.INSERT})
    public void onSpuChanged(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        String Spu_id = getSpuId(eventType, rowData);
        System.out.println("Spu_id: " + Spu_id);
        if (Spu_id != null){
            pageFeign.createHtmlPage(Long.valueOf(Spu_id));
        }
    }

    private String getSpuId(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        if (eventType == CanalEntry.EventType.DELETE){
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()){
                if (column.getName().equalsIgnoreCase("id")){
                    return null;
                }
            }
        }else {
            for (CanalEntry.Column column : rowData.getAfterColumnsList()){
                if (column.getName().equalsIgnoreCase("id")){
                    return column.getValue();
                }
            }
        }
        return null;
    }
}
