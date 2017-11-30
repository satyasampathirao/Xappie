package com.xappie.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xappie.models.NotificationModel;

public class NotificationDataSource {
    private SQLiteDatabase mDatabase;
    private DatabaseHandler mDataBaseHelper;
    private String mColumns[] = {DBConstants.NOTIFICATION_ID,
            DBConstants.NOTIFICATION_CATEGORY,
            DBConstants.NOTIFICATION_TITLE,
            DBConstants.NOTIFICATION_IMAGE_URL,
            DBConstants.NOTIFICATION_SOURCE,
            DBConstants.NOTIFICATION_TIME};
    private Context mContext;

    public NotificationDataSource(Context context) {
        mContext = context;
        if (mContext != null) {
            mDataBaseHelper = new DatabaseHandler(mContext);
        }
    }

    private void open() {
        if (mDataBaseHelper != null) {
            mDatabase = mDataBaseHelper.getWritableDatabase();
        }
    }

    private void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public long insertData(NotificationModel model) {
        long insertValue = -1;

        ContentValues values = new ContentValues();
        values.put(DBConstants.NOTIFICATION_CATEGORY, model.getCategory());
        values.put(DBConstants.NOTIFICATION_TITLE, model.getTitle());
        values.put(DBConstants.NOTIFICATION_TIME, model.getTime());
        values.put(DBConstants.NOTIFICATION_SOURCE, model.getSource());
        values.put(DBConstants.NOTIFICATION_IMAGE_URL, model.getImage_url());

        open();
        insertValue = mDatabase.insert(DBConstants.TABLE_NOTIFICATION_HISTORY, null,
                values);
        close();

        return insertValue;
    }

    public ArrayList<NotificationModel> selectAll() {
        ArrayList<NotificationModel> modelsList = new ArrayList<>();
        open();
        Cursor cursor = mDatabase.query(DBConstants.TABLE_NOTIFICATION_HISTORY, mColumns,
                null, null, null, null, mColumns[0] + " DESC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                NotificationModel model = new NotificationModel();
                model.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.NOTIFICATION_ID)));
                model.setCategory(cursor.getString(cursor
                        .getColumnIndex(DBConstants.NOTIFICATION_CATEGORY)));
                model.setTitle(cursor.getString(cursor
                        .getColumnIndex(DBConstants.NOTIFICATION_TITLE)));
                model.setImage_url(cursor.getString(cursor
                        .getColumnIndex(DBConstants.NOTIFICATION_IMAGE_URL)));
                model.setTime(cursor.getString(cursor
                        .getColumnIndex(DBConstants.NOTIFICATION_TIME)));
                model.setSource(cursor.getString(cursor.getColumnIndex(DBConstants.NOTIFICATION_SOURCE)));
                modelsList.add(model);
            }
        }
        cursor.close();
        close();
        return modelsList;
    }

    public int getUnReadNotificationCount() {
        int notificationCount = -1;
        open();
        Cursor cursor = mDatabase.query(DBConstants.TABLE_NOTIFICATION_HISTORY, mColumns,
                mColumns[4] + " = ?", new String[]{"N"}, null, null, null);
        if (cursor.getCount() > 0) {
            notificationCount = cursor.getCount();
        }
        cursor.close();
        close();
        return notificationCount;
    }

    public boolean isNotificationOpen(int id) {
        boolean isRead = false;
        open();
        Cursor cursor = mDatabase.query(DBConstants.TABLE_NOTIFICATION_HISTORY, mColumns,
                mColumns[0] + " = ?", new String[]{"" + id}, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(DBConstants.NOTIFICATION_ISOPENED)).equals("Y")) {
                    isRead = true;
                }
            }
        }
        cursor.close();
        close();
        return isRead;
    }

    public int update(int id, String isOpened) {
        int updateValue = -1;
        ContentValues values = new ContentValues();
        values.put(DBConstants.NOTIFICATION_ISOPENED, isOpened);

        open();
        updateValue = mDatabase.update(DBConstants.TABLE_NOTIFICATION_HISTORY, values, mColumns[0] + " = ?", new String[]{"" + id});
        close();
        return updateValue;
    }

    public int delete(int id) {
        int deleteValue = -1;

        open();
        deleteValue = mDatabase.delete(DBConstants.TABLE_NOTIFICATION_HISTORY, mColumns[0] + " = ?", new String[]{"" + id});
        close();

        return deleteValue;
    }

    public int deleteAll() {
        int deleteValue = -1;
        open();
        deleteValue = mDatabase.delete(DBConstants.TABLE_NOTIFICATION_HISTORY, null, null);
        close();

        return deleteValue;
    }

}
