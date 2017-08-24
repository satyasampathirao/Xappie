package com.xappie.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/24/2017.
 */

public class LanguageListModel extends Model {
    private ArrayList<LanguageModel> languageModels;

    public ArrayList<LanguageModel> getLanguageModels() {
        return languageModels;
    }

    public void setLanguageModels(ArrayList<LanguageModel> languageModels) {
        this.languageModels = languageModels;
    }
}
