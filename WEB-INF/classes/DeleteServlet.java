
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.PrintWriter;
import java.util.*;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            String id = request.getParameter("id");

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

            final DeleteRequest reques = new DeleteRequest("posts", id);

            DeleteResponse res = client.delete(reques, RequestOptions.DEFAULT);

            out.println("<p>Successfully deleted..");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
