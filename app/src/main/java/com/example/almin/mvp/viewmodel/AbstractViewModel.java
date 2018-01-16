package com.example.almin.mvp.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okio.Buffer;

/**
 * Created by almin on 2017/12/19.
 */

public abstract class AbstractViewModel extends ViewModel{
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static void main(String[] args) {
        TypeAdapter adapter;
        Gson gson = new Gson();
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        try {
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        adapter.write(jsonWriter, value);
//        jsonWriter.close();
        System.out.println("   buffer.readByteString()    "+buffer.readByteString());
    }
}
