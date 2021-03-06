//
// Java Client Library for Treasure Data Cloud
//
// Copyright (C) 2011 - 2012 Muga Nishizawa
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package com.treasure_data.model;

public class Job extends AbstractModel {

    public static enum Status {
        QUEUED, BOOTING, RUNNING, SUCCESS, ERROR, KILLED, UNKNOWN,
    }

    public static Status toStatus(String statusName) {
        if (statusName == null) {
            throw new NullPointerException();
        }

        if (statusName.equals("queued")) {
            return Status.QUEUED;
        } else if (statusName.equals("booting")) {
            return Status.BOOTING;
        } else if (statusName.equals("running")) {
            return Status.RUNNING;
        } else if (statusName.equals("success")) {
            return Status.SUCCESS;
        } else if (statusName.equals("error")) {
            return Status.ERROR;
        } else if (statusName.equals("killed")) {
            return Status.KILLED;
        } else {
            return Status.UNKNOWN;
        }
    }

    public static String toStatusName(Status status) {
        if (status == null) {
            throw new NullPointerException();
        }

        switch (status) {
        case QUEUED:
            return "queued";
        case BOOTING:
            return "booting";
        case RUNNING:
            return "running";
        case SUCCESS:
            return "success";
        case ERROR:
            return "error";
        case KILLED:
            return "killed";
        default:
            return "unknown";
        }
    }

    public static enum Type {
        HIVE, UNKNOWN,
    }

    public static Type toType(String typeName) {
        if (typeName == null) {
            throw new NullPointerException();
        }

        if (typeName.equals("hive")) {
            return Type.HIVE;
        } else {
            return Type.UNKNOWN;
        }
    }

    public static String toTypeName(Type type) {
        if (type == null) {
            throw new NullPointerException();
        }

        switch (type) {
        case HIVE:
            return "hive";
        default:
            return "unknown";
        }
    }

    private Type type;

    private Database database;

    private String url;

    private Status status;

    private String createdAt;

    private String startAt;

    private String endAt;

    private String query;

    private String resultTable;

    private String resultSchema; // hive-specific schema

    public Job(String jobID) {
        super(jobID);
    }

    public Job(Database database, String sql) {
        super(null);
        this.type = Type.HIVE;
        this.database = database;
        this.query = sql;
    }

    public Job(String jobID, Job.Type type, Database database, String query, String url) {
        super(jobID);
        this.type = type;
        this.database = database;
        this.query = query;
        this.url = url;
    }

    public Job(String jobID, Job.Type type, Status status, String startAt,
            String endAt, String query, String resultTable, String resultSchema) {
        super(jobID);
        this.type = type;
        this.status = status;
        this.startAt = startAt;
        this.endAt = endAt;
        this.query = query;
        this.resultTable = resultTable;
        this.resultSchema = resultSchema;
    }

    public void setJobID(String jobID) {
        this.setName(jobID);
    }

    public String getJobID() {
        return getName();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Job.Type getType() {
        return type;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    public void setStatus(Job.Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setCreatedAt(String time) {
        this.createdAt = time;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setStartAt(String time) {
        this.startAt = time;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setEndAt(String time) {
        this.endAt = time;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setQuery(String q) {
        query = q;
    }

    public String getQuery() {
        return query;
    }

    public String getResultTable() {
        return resultTable;
    }

    public String getResultSchema() {
        return resultSchema;
    }
}
