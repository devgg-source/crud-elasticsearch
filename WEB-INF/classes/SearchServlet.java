
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.lucene.search.BooleanQuery;

import java.util.*;
import java.io.*;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.builder.SearchSourceBuilder;
// import org.elasticsearch.action.search.SearchSourceBuilder;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
// import org.elasticsearch.search.SearchHit.getSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost("localhost", 9200, "http")));

            String name = request.getParameter("name");

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.termQuery("user", name));
            // sourceBuilder.from(0);
            // sourceBuilder.size(5);
            // sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("posts");
            searchRequest.source(sourceBuilder);
            final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            // String s1 = "", s2 = "";
            PrintWriter out = response.getWriter();

            // out.print("<h3>Search Result</h3>");

            out.print("<table border='1' width='100%'><tr><th>Name</th><th>Message</th></tr>");

            for (SearchHit hits : searchResponse.getHits()) {
                Map<String, Object> map = hits.getSourceAsMap();
                out.print("<tr>");

                out.print("<td>" + map.get("user").toString() + "</td>");
                out.print("<td>" + map.get("message").toString() + "</td>");
                out.print("</tr>");

            }

            out.print("</table>");

            // for (final Entry<String, String> entry :
            // StringByteIterator.getStringMap(values).entrySet()) {
            // hit.getSource().put(entry.getKey(), entry.getValue());
            // }
            // Map<String, Object> responseFields;
            // Set uniqueValues = new HashSet<Entry<String, Object>>();

            // responseFields = (Map<String, Object>) hit.getSource();
            // responseFields.toString();

            // Iterator it = responseFields.entrySet().iterator();
            // while (it.hasNext()) {
            // Map.Entry pair = (Map.Entry) it.next();
            // uniqueValues.add(pair);
            // // use list here

            // }

            // SearchHits hits = searchResponse.getHits();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
