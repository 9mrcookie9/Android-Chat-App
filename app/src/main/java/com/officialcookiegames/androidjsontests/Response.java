package com.officialcookiegames.androidjsontests;

import android.util.Log;

import java.util.List;

/**
 * Created by Kacper on 2016-06-23.
 */
public class Response {

    /**
     * id : 1
     * nick : test
     * message : test
     * date : 2016-06-29 20:42
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String nick;
        private String message;
        private String date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public boolean equals(Response object2){
        if(this.getData() == null || object2.getData() == null)
            return false;

        if(this.getData().size() == object2.getData().size()){
            for (int i = 0; i < this.getData().size(); i++) {
                boolean toReturn = true;
                toReturn = (this.getData().get(i).getDate().matches(object2.getData().get(i).getDate()))?true:false;
                if(toReturn)
                toReturn = (this.getData().get(i).getId().matches(object2.getData().get(i).getId()))?true:false;
                if(toReturn)
                toReturn = (this.getData().get(i).getMessage().matches(object2.getData().get(i).getMessage()))?true:false;
                if(toReturn)
                toReturn = (this.getData().get(i).getNick().matches(object2.getData().get(i).getNick()))?true:false;
                if(toReturn == false)
                    return false;
            }
            return true;
        }
        return false;
    }
}
