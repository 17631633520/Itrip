package cn.bdqn.dao;

import cn.bdqn.entity.ItripHotelVO;
import cn.bdqn.entity.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;

public class BaseSolr {
    String url="http://localhost:8080/solr-4.9.1/Hotel_Core/";
    HttpSolrClient SolrClient;
    public BaseSolr(){
        SolrClient=new HttpSolrClient(url);
        SolrClient.setParser(new XMLResponseParser());
        SolrClient.setConnectionTimeout(500);
    }

    public Page<ItripHotelVO>GetlistBypage(SolrQuery solrQuery,Integer pageNo,Integer pageSize) throws Exception {
        //设置分页开始位置和每页多少数据
        solrQuery.setQuery("*:*");
        solrQuery.setStart((pageNo-1)*pageSize);
        solrQuery.setRows(pageSize);
        //通过response接收返回的数据
        QueryResponse queryResponse=SolrClient.query(solrQuery);
        List<ItripHotelVO>list=((QueryResponse)queryResponse).getBeans(ItripHotelVO.class);
        //查询分页前的总条数
        SolrDocumentList solrDocuments=((QueryResponse)queryResponse).getResults();
        Integer count=new Long(solrDocuments.getNumFound()).intValue();
        //构建page类中数据
        Page<ItripHotelVO>page=new Page(pageNo,pageSize,count);
        page.setRows(list);
        return page;
    }

    public List<ItripHotelVO>Getlist(SolrQuery solrQuery) throws Exception {
        solrQuery.setQuery("*:*");
        solrQuery.setStart(0);
        solrQuery.setRows(100);
        QueryResponse queryResponse=SolrClient.query(solrQuery);
        List<ItripHotelVO> list=queryResponse.getBeans(ItripHotelVO.class);
        return list;
    }
}
