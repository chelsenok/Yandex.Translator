package com.chelsenok.translator.asynctasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class RetrieveJsonTask extends AsyncTask<String, Void, JSONObject> {

    protected JSONObject doInBackground(final String... urls) {
        InputStream is = null;
        JSONObject json = null;
        try {
            is = new URL(urls[0]).openStream();
            final BufferedReader rd = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            final String jsonText = readAll(rd);
            json = new JSONObject(jsonText);
        } catch (final Exception ignored) {
        } finally {
            try {
                is.close();
            } catch (final IOException ignored) {
            }
            return json;
        }
    }

    private String readAll(final Reader rd) throws IOException {
        final StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
