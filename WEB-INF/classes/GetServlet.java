
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

// import org.elasticsearch.action.search.SearchSourceBuilder;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

@WebServlet("/GetServlet")
public class GetServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        // final Logger logger = LogManager.getLogger(Getapi.class.getName());

        // int scrollSize = 1000;
        // List<Map<String, Object>> esData = new ArrayList<Map<String, Object>>();
        // SearchResponse response = null;
        // int i = 0;
        // try {
        // while (response == null || response.getHits().hits().length != 0) {
        // response =
        // client.prepareSearch("posts").setTypes("Post").setQuery(QueryBuilders.matchAllQuery())
        // .setSize(scrollSize).setFrom(i * scrollSize).execute().actionGet();
        // for (SearchHit hit : response.getHits()) {
        // esData.add(hit.getSource());
        // }
        // i++;
        // }
        // System.out.println(esData);
        // } catch (final IOException e) {
        // throw new RuntimeException(e);
        // }
        PrintWriter out = response.getWriter();

        try {
            SearchRequest searchRequest = new SearchRequest("posts");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.sort(new FieldSortBuilder("postDate").order(SortOrder.DESC));
            searchRequest.source(searchSourceBuilder);
            SearchResponse res = client.search(searchRequest, RequestOptions.DEFAULT);

            out.print("<table border='1' width='100%'><tr><th>Name</th><th>Message</th></tr>");

            for (SearchHit hits : res.getHits()) {
                Map<String, Object> map = hits.getSourceAsMap();
                out.print("<tr>");

                out.print("<td>" + map.get("user").toString() + "</td>");
                out.print("<td>" + map.get("message").toString() + "</td>");
                out.print("</tr>");

            }

            out.print("</table>");
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

    }
}
