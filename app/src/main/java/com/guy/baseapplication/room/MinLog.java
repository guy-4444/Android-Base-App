package com.guy.baseapplication.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "min_data")
public class MinLog {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "tag")
    public String tag = "";

    @ColumnInfo(name = "message")
    public String message = "";

    @ColumnInfo(name = "time")
    public long time = 0;

    @Ignore
    public int samples = 0;

    @Ignore
    public int active = 0;


    public MinLog() { }

    public MinLog(MinLog ml) {
        this.tag = ml.tag;
        this.time = ml.time;
    }

    public MinLog(String tag, String message, long time) {
        this.tag = tag;
        this.message = message;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public MinLog setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MinLog setMessage(String message) {
        this.message = message;
        return this;
    }

    public long getTime() {
        return time;
    }

    public MinLog setTime(long time) {
        this.time = time;
        return this;
    }

    public int getSamples() {
        return samples;
    }

    public MinLog setSamples(int samples) {
        this.samples = samples;
        return this;
    }

    public int getActive() {
        return active;
    }

    public MinLog setActive(int active) {
        this.active = active;
        return this;
    }
}
