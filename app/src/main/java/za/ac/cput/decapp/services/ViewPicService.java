package za.ac.cput.decapp.services;

import android.content.Context;

/**
 * Created by User on 2016/05/04.
 */
public interface ViewPicService {
    void addPic (Context context, PicResource picResource);
    void resetPic(Context context);
}
