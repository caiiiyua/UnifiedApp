package org.caiiiyua.unifiedapp.provider;


import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DBHelper {

    public static final int DATABASE_VERSION = 1;
    public DatabaseFactory mDatabaseFactory;

    public void setDatabaseFactory(DatabaseFactory dbf) {
        mDatabaseFactory = dbf;
    }

    public DatabaseFactory getDatabaseFactory() {
        return mDatabaseFactory;
    }

    protected final class DatabaseHelper extends SQLiteOpenHelper {
        Context mContext;

        public DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            LogUtils.d(LogUtils.TAG, "Creating EmailProvider database");
            // Create all tables here; each class has its own method
            if (db == null) {
                return;
            }
            createTable(db);
            
        }

        public void createTable(SQLiteDatabase db) {

            if (mDatabaseFactory == null) {
                return;
            }
            String tableName = mDatabaseFactory.getTableName();
            String tableColmns = mDatabaseFactory.toString();
            String createString = "(" + DBContent.RECORD_ID + "integer primary key autoincrement, "
                    + tableColmns;
            String altCreateString = "(" + DBContent.RECORD_ID + "integer unique, "
                    + tableColmns;
            // The table is created with schema here
            db.execSQL("create table " + tableName + createString);

            String indexColumns[] = mDatabaseFactory.getIndexColumns();
            if (indexColumns.length > 0) {
                for (String columnName : indexColumns) {
                    db.execSQL(createIndex(tableName, columnName));
                }
            }
        }

        // Create a table from Database factory
        public void createTable(SQLiteDatabase db, DatabaseFactory dbFactory) {
            if (dbFactory == null) {
                return;
            }
            String tableName = dbFactory.getTableName();
            String tableColmns = dbFactory.toString();
            String createString = "(" + DBContent.RECORD_ID + "integer primary key autoincrement, "
                    + tableColmns;
            String altCreateString = "(" + DBContent.RECORD_ID + "integer unique, "
                    + tableColmns;
            // The table is created with schema here
            db.execSQL("create table " + tableName + createString);

            String indexColumns[] = dbFactory.getIndexColumns();
            if (indexColumns.length > 0) {
                for (String columnName : indexColumns) {
                    db.execSQL(createIndex(tableName, columnName));
                }
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            
        }
        
    }

    public String createIndex(String tableName, String columnName) {
        /*
         * Internal helper method for index creation.
         * Example:
         * "create index message_" + MessageColumns.FLAG_READ
         * + " on " + Message.TABLE_NAME + " (" + MessageColumns.FLAG_READ + ");"
         */
        /* package */
        return "create index " + tableName.toLowerCase() + '_' + columnName
                + " on " + tableName + " (" + columnName + ");";
    }
}
