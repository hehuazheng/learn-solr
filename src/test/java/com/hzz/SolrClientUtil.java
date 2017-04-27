package com.hzz;

import com.google.common.collect.Maps;
import com.hzz.util.HbaseUtil;
import com.hzz.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SolrClientUtil {
    private static String zkHost = "10.185.240.151,10.185.240.152,10.185.240.153/solr";

    @Test
    public void addOneItem() throws IOException, SolrServerException {
        CloudSolrClient client = new CloudSolrClient(zkHost, false);
        SolrInputDocument doc = new SolrInputDocument();
        String productId = "1120";
        doc.addField("id", HbaseUtil.makeBaseRowKeyStr(productId));
        doc.addField("name_bs", 10);
        doc.addField("addr_bs", 8);
        doc.addField("productName", "hzz" + productId);
        doc.addField("productId", productId);
        UpdateResponse response = client.add("search_product4", doc);
        System.out.println("res code " + response.getStatus());
        client.close();
    }

    @Test
    public void deleteOneItem() throws Exception {
        CloudSolrClient client = new CloudSolrClient(zkHost, false);
        String pk = HbaseUtil.makeBaseRowKeyStr("1119");
        UpdateResponse response = client.deleteById("search_product4", pk);
        System.out.println("res code " + response.getStatus());
        client.close();
    }

    @Test
    public void getItem() throws IOException, SolrServerException {
        CloudSolrClient client = new CloudSolrClient(zkHost, false);
        SolrQuery query = new SolrQuery();
        query.setQuery("productId:1119");
//        query.set("productId", "1119");
        QueryResponse response = client.query("search_product4", query);
        SolrDocumentList documentList = response.getResults();
        for (int i = 0; i < documentList.getNumFound(); i++) {
            SolrDocument doc = documentList.get(i);
            for (String fieldName : doc.getFieldNames()) {
                System.out.println(fieldName + " :  " + doc.getFieldValue(fieldName));
            }
        }
        client.close();
    }

    @Test
    public void addField() throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "newField");
        map.put("type", "long");
        map.put("multiValued", false);
        map.put("indexed", false);
        map.put("required", false);
        map.put("stored", true);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("add-field", map);
        String url = "http://solrserver:15000/solr/search_product4/schema";
        StringEntity entity = new StringEntity(JsonUtils.toJsonString(params));
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String resp = IOUtils.toString(responseEntity.getContent());
        EntityUtils.consume(entity);
        System.out.println(resp);
    }

    @Test
    public void addDynamicField() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "*_bs");
        map.put("type", "long");
        map.put("multiValued", false);
        map.put("indexed", false);
        map.put("required", false);
        map.put("stored", true);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("add-dynamic-field", map);
        String url = "http://solrserver:15000/solr/search_product4/schema";
        StringEntity entity = new StringEntity(JsonUtils.toJsonString(params));
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String resp = IOUtils.toString(responseEntity.getContent());
        EntityUtils.consume(entity);
        System.out.println(resp);
    }

    @Test
    public void deleteField() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "boost");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("delete-field", map);
        String url = "http://solrserver:15000/solr/search_product4/schema";
        StringEntity entity = new StringEntity(JsonUtils.toJsonString(params));
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String resp = IOUtils.toString(responseEntity.getContent());
        EntityUtils.consume(entity);
        System.out.println(resp);
    }

    @Test
    public void deleteDynamicField() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "*_bs");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("delete-dynamic-field", map);
        String url = "http://solrserver:15000/solr/search_product4/schema";
        StringEntity entity = new StringEntity(JsonUtils.toJsonString(params));
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String resp = IOUtils.toString(responseEntity.getContent());
        EntityUtils.consume(entity);
        System.out.println(resp);
    }

    @Test
    public void replaceField() {
    }

    @Test
    public void replaceDynamicField() {
    }

    @Test
    public void getSolrUrl() {
        CloudSolrClient solrClient = new CloudSolrClient(zkHost, false);
        try {
            solrClient.connect();
            Set<String> liveNodes = solrClient.getZkStateReader()
                    .getClusterState().getLiveNodes();
            if (liveNodes.isEmpty()) {
                throw new RuntimeException("没有可用的服务节点");
            }
            String url = solrClient.getZkStateReader().getBaseUrlForNodeName(
                    liveNodes.iterator().next());
            System.out.println(url);
        } finally {
            try {
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
