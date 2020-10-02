package com.develop.eventmanagement;

public class LoginResp {


    /**
     * status : true
     * message : User login successful.
     * data : {"id":"5","first_name":"jithu","last_name":"r","email":"jithu@test.com","password":"0e7517141fb53f21ee439b355b5a1d0a","phone":"1234567890","created":"2020-09-30 10:28:27","modified":"2020-09-30 10:28:27","status":"1"}
     */

    private boolean status;
    private String message;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5
         * first_name : jithu
         * last_name : r
         * email : jithu@test.com
         * password : 0e7517141fb53f21ee439b355b5a1d0a
         * phone : 1234567890
         * created : 2020-09-30 10:28:27
         * modified : 2020-09-30 10:28:27
         * status : 1
         */

        private String id;
        private String first_name;
        private String last_name;
        private String email;
        private String password;
        private String phone;
        private String created;
        private String modified;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
