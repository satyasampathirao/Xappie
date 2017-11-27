package com.xappie.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Santosh on 21-11-2017.
 */

public class JobsListModel extends Model implements Serializable {
    private ArrayList<JobsModel> jobsModels;

    public ArrayList<JobsModel> getJobsModels() {
        return jobsModels;
    }

    public void setJobsModels(ArrayList<JobsModel> jobsModels) {
        this.jobsModels = jobsModels;
    }
}
