import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.PrintWriter;
import java.util.*;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
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

            Map<String, Object> document = new HashMap<>();
            document.put("user", name);
            // document.put("postDate", new Date());
            document.put("message", message);

            final UpdateRequest reques = new UpdateRequest("posts", id).doc(document);

            client.update(reques, RequestOptions.DEFAULT);

            out.print("<p>Updated Successfully</p>");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
