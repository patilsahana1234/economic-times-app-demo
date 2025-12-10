package com.app;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsServlet extends HttpServlet {

    private static final String API_KEY = "62b480461ea186b2249f04345692a128";
    private static final String API_URL =
        "https://gnews.io/api/v4/top-headlines?category=business&lang=en&country=in&apikey=";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            URL url = new URL(API_URL + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            out.print(response.toString());
        } catch (Exception e) {
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
