package com.guy.baseapplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MinLog.class}, version = 2)
abstract class AppDatabase extends RoomDatabase {
    public abstract MinLogDao minLogDao();
}
