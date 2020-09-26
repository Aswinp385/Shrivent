package com.develop.eventmanagement;

import java.util.List;

public class EventModel {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * event_title : Holy Prayer
         * event_description : An event
         * event_date : 2021-01-01
         * event_time : 12.00 to 03.00
         * image : image/file.jpg
         * price : 200
         * guest :
         * location :
         */

        private int id;
        private String event_title;
        private String event_description;
        private String event_date;
        private String event_time;
        private String image;
        private String price;
        private String guest;
        private String location;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEvent_title() {
            return event_title;
        }

        public void setEvent_title(String event_title) {
            this.event_title = event_title;
        }

        public String getEvent_description() {
            return event_description;
        }

        public void setEvent_description(String event_description) {
            this.event_description = event_description;
        }

        public String getEvent_date() {
            return event_date;
        }

        public void setEvent_date(String event_date) {
            this.event_date = event_date;
        }

        public String getEvent_time() {
            return event_time;
        }

        public void setEvent_time(String event_time) {
            this.event_time = event_time;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGuest() {
            return guest;
        }

        public void setGuest(String guest) {
            this.guest = guest;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
