import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SaveServlet")
public class SaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            String id = request.getParameter("id");

            String name = request.getParameter("name");

            String message = request.getParameter("message");

            // Emp e=new Emp();
            // e.setName(name);
            // e.setPassword(password);
            // e.setEmail(email);
            // e.setCountry(country);

            // int status=EmpDao.save(e);
            // if(status>0){
            // out.print("<p>Record saved successfully!</p>");
            // request.getRequestDispatcher("index.html").include(request, response);
            // }else{
            // out.println("Sorry! unable to save record");
            // }

            // Localhostclient lh = new Localhostclient();
            final RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost("localhost", 9200, "http")));
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("user", name);
            jsonMap.put("postDate", new Date());
            jsonMap.put("message", message);
            IndexRequest reques = new IndexRequest("posts").id(id).source(jsonMap);
            IndexResponse res = client.index(reques, RequestOptions.DEFAULT);

            if (res.status() == RestStatus.CREATED) {
                out.print("<p>Successfully Created..</p>");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
