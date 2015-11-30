package com.example.angel.noteboard;


import android.app.Fragment;
import android.graphics.Picture;

import java.util.Date;

public class PictureNoteActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        String date = getIntent().getSerializableExtra(PictureNoteFragment.EXTRA_PICTURE_DATE).toString();

        return PictureNoteFragment.newInstance(date);
    }
}
