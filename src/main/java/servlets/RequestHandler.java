package servlets;

import domain.Book;
import domain.Document;
import domain.Journal;
import domain.Topic;
import service.WatermarkService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by olgasyrova on 08.09.16.
 */
public class RequestHandler extends HttpServlet {

    /**
     * method is used to accept requests with documents to be watermarked, after a document is successfully submitted
     * to processing, 202 response is sent and ticket (uuid) of the process
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        Document document;
        if (genre != null && !"no".equalsIgnoreCase(genre)){
            document = new Book(title, author, Topic.valueOf(genre.toUpperCase()));
        } else {
            document = new Journal(title, author);
        }
        UUID uuid = WatermarkService.addDocument(document);
        response.setContentType("text/html");
        response.setStatus(202);
        response.getWriter().print(uuid);
        //request.setAttribute("uuid", uuid);
        //request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    /**
     * method is used to poll the watermarked document by its id, if the document with the specified id is ready
     * it is displayed in the browser, if not
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("uuid") != null){
            UUID uuid = UUID.fromString(request.getParameter("uuid"));
            Document document = WatermarkService.pollDocument(uuid);
            response.getWriter().print(document.getWatermark().toString());
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
