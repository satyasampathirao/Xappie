package com.xappie.models;

/**
 * Created by Shankar on 9/1/2017.
 */

public class EntertainmentTopStoriesDetailModel extends Model{
    private EntertainmentModel mCurrentDetailModel;
    private EntertainmentModel mNextDetailModel;

    public EntertainmentModel getmCurrentDetailModel() {
        return mCurrentDetailModel;
    }

    public void setmCurrentDetailModel(EntertainmentModel mCurrentDetailModel) {
        this.mCurrentDetailModel = mCurrentDetailModel;
    }

    public EntertainmentModel getmNextDetailModel() {
        return mNextDetailModel;
    }

    public void setmNextDetailModel(EntertainmentModel mNextDetailModel) {
        this.mNextDetailModel = mNextDetailModel;
    }
}
