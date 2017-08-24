package com.xappie.aynctaskold;


import com.xappie.models.Model;

/**
 * Created by Shankar on 3/7/2017.
 */

public interface IAsyncCaller {
    void onComplete(Model model);
}
