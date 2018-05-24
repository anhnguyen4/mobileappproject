package com.example.sipln.mobile_dict_app.Models;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.sipln.mobile_dict_app.Services.NotifyService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Word {

    private String word;
    private String meaning;

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }


    public Word(String word) {
        this.word = word;
        this.meaning = "";
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Word {keyword=" + word + ", meaning=" + meaning + "}";
    }

}
