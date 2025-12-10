package com.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewsServlet extends HttpServlet {

    private static final String API_URL =
            "https://gnews.io/api/v4/top-headlines?apikey=62b480461ea186b2249f04345692a128&lang=en&country=in";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder jsonData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }

            reader.close();

            JSONObject json = new JSONObject(jsonData.toString());
            JSONArray articles = json.getJSONArray("articles");

            out.println("<html><body>");
            out.println("<h1>Economic Times - Top Headlines (Live)</h1>");
            out.println("<ul>");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);

                String title = article.getString("title");
                String link = article.getString("url");

                out.println("<li><a href='" + link + "' target='_blank'>" + title + "</a></li>");
            }

            out.println("</ul>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<h3>Error fetching news: " + e.getMessage() + "</h3>");
        }
    }
}
