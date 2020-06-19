package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.*;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Map<String, Controller> controllers;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.controllers = initializeController();
    }

    private Map<String, Controller> initializeController() {
        Map<String, Controller> controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
        return controllers;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.of(br);
            HttpResponse httpResponse = new HttpResponse();
            if (httpRequest.isStaticFileRequest()) {
                httpResponse = HttpResponse.of(httpRequest);
                httpResponse.response(dos);
            } else {
                Controller controller = controllers.get(httpRequest.getHost());
                ModelAndView mav = controller.service(httpRequest, httpResponse);
                httpResponse.response(dos, mav, httpRequest);
            }
            dos.flush();
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
