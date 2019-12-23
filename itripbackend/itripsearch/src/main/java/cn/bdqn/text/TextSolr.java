package cn.bdqn.text;

import cn.bdqn.entity.HoteEntity;
import cn.bdqn.entity.ItripHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public class TextSolr {
    public static void main(String[] args) throws Exception, SolrServerException {
        String url="http://localhost:8080/solr-4.9.1/Hotel_Core/";
        HttpSolrClient httpSolrClient=new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser());
        httpSolrClient.setConnectionTimeout(500);
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setStart(0);
        solrQuery.setRows(100);
        QueryResponse queryResponse=httpSolrClient.query(solrQuery);
        List<ItripHotelVO> hoteEntity=queryResponse.getBeans(ItripHotelVO.class);
        for (ItripHotelVO hotel:hoteEntity) {
            System.out.println(hotel.getId()+hotel.getHotelName()+hotel.getExtendPropertyNames());
        }
    }
}
