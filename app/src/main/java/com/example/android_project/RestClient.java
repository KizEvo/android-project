package com.example.android_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

// Callback interface for REST responses
interface RestCallback {
    void onSuccess(String response);
    void onFailure(String error);
}

public class RestClient {
    private static final String TAG = "RestClient";
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;

    public RestClient(int timeout) {
        // Initialize OkHttpClient with 30-second timeouts
        client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }

    public void post(String message, int maxOutputTokens, String apiKey, RestCallback callback) {
        // Build the full URL with API key
        String url = BASE_URL + apiKey;

        // JSON payload
        String jsonPayload = "{\n" +
                "  \"contents\": [{\n" +
                "    \"parts\": [{\"text\": \"" + message + "\"}]\n" +
                "  }]\n" +
                "}";

        // Add maxOutputTokens to the payload
        JSONObject payload = null;
        try {
            payload = new JSONObject(jsonPayload);
            JSONObject generationConfig = new JSONObject();
            generationConfig.put("maxOutputTokens", maxOutputTokens);
            payload.put("generationConfig", generationConfig);
        } catch (JSONException e) {
            callback.onFailure("JSON parsing error: " + e.getMessage());
            return;
        }

        // Create request body
        RequestBody body = RequestBody.create(payload.toString(), JSON);

        // Build the request
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network or other errors
                callback.onFailure("Request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Parse response body into JSONObject
                    try {
                        // JSON layout
                        /*
                         {
                          "candidates": [
                            {
                              "content": {
                                "parts": [
                                  {
                                   "text": "..."
                                  }
                                ],
                                "role": "model"
                              },
                              "finishReason": "STOP",
                              "citationMetadata": {
                                "citationSources": [
                                  {
                                    "startIndex": 273,
                                    "endIndex": 406,
                                    "uri": "https://brydris.com/2024/03/09/the-future-of-artificial-intelligence/"
                                  }
                                ]
                              },
                              "avgLogprobs": -0.22333751826864631
                            }
                          ],
                          "usageMetadata": {
                            "promptTokenCount": 4,
                            "candidatesTokenCount": 1748,
                            "totalTokenCount": 1752,
                            "promptTokensDetails": [ {"modality": "TEXT", "tokenCount": 4} ],
                            "candidatesTokensDetails": [ {"modality": "TEXT", "tokenCount": 1748} ]
                          },
                          "modelVersion": "gemini-2.0-flash"
                        }
                        */

                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        JSONArray candidates = jsonResponse.getJSONArray("candidates");
                        JSONObject candidate = candidates.getJSONObject(0);
                        JSONObject content = candidate.getJSONObject("content");
                        JSONArray parts = content.getJSONArray("parts");
                        JSONObject part = parts.getJSONObject(0);
                        String generatedText = part.getString("text");
                        callback.onSuccess(generatedText);
                    } catch (JSONException e) {
                        callback.onFailure("JSON parsing error: " + e.getMessage());
                    }
                } else {
                    // Handle HTTP error
                    callback.onFailure("Error: " + response.code() + " " + response.message());
                }
            }
        });
    }
}