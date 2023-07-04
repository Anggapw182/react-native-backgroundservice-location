package com.backgroundservicelocation.locationservice;

import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostLocation {
    private static final String TAG = "Location Log";
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    public interface OnDataCollectedCallback {
        void onDataCollected(String data);
    }

    private final OnDataCollectedCallback mCallback;

    public PostLocation(OnDataCollectedCallback callback) {
        mCallback = callback;
    }

    public void execute(String... strings) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CompletableFuture.supplyAsync(() -> doInBackground(strings), EXECUTOR)
                    .whenCompleteAsync(this::onPostExecute, EXECUTOR);
        }
    }

    private String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String result = null;

        try {
            URL url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            if (strings.length > 2) {
                urlConnection.setRequestProperty("Authorization", "Bearer " + strings[2]);
            }
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            OutputStream outputStream = urlConnection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            writer.write(strings[1]);
            writer.flush();

            Log.v(TAG, strings[1]);

            int responseCode = urlConnection.getResponseCode();

            Log.v(TAG, "Success " + responseCode);
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            Log.v(TAG, "Response Body: " + sb);

            try {
                JSONObject jsonObject = new JSONObject(sb.toString());
                result = jsonObject.getString("statusCode");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            Log.v(TAG, e.getLocalizedMessage());
            result = "Exception: " + e.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.v(TAG, "Error closing stream", e);
                }
            }
        }

        return result;
    }

    private void onPostExecute(String s, Throwable throwable) {
        if (throwable != null) {
            Log.e(TAG, "Error occurred during task execution", throwable);
        }
        if (mCallback != null) {
            mCallback.onDataCollected(s);
        }
    }
}
