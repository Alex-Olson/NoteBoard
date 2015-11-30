package com.example.angel.noteboard;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

public class PictureNoteFragment extends Fragment {
    private PictureNote mNote;
    private ImageView mImage;
    private EditText mTags;
    private TextView mDateDisplay;
    Uri imageFileUri;
    private static final int TAKE_PICTURE_REQUEST = 0;
    Bitmap image;

    String date;
    String filename;
    public static final String EXTRA_PICTURE_DATE = "com.example.angel.noteboard.picture_date";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


      //  mNote = (PictureNote) NoteManager.get(getActivity()).getNote(date);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_picture_note, parent, false);
        date = getArguments().getSerializable(EXTRA_PICTURE_DATE).toString();
        mNote = (PictureNote) NoteManager.get(getActivity()).getNote(date);

        mDateDisplay = (TextView)v.findViewById(R.id.picture_note_date);
        mDateDisplay.setText(mNote.getDate().toString());

        mImage = (ImageView)v.findViewById(R.id.picture);

        if (mNote.getUriString() == null){
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            filename = mNote.getDate() + ".jpg";

            File file = new File(Environment.getExternalStorageDirectory(), filename);

            imageFileUri = Uri.fromFile(file);

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);

            if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
                startActivityForResult(pictureIntent, TAKE_PICTURE_REQUEST);
                mNote.setUriString(imageFileUri.toString());
                image = scaleBitmap(Uri.parse(mNote.getUriString()));
                mImage.setImageBitmap(image);


            } else {
                Toast.makeText(getActivity(), "No camera available", Toast.LENGTH_SHORT).show();
            }
        } else {
            imageFileUri = Uri.parse(mNote.getUriString());
            image = scaleBitmap(imageFileUri);
            mImage.setImageBitmap(image);

        }





        mTags = (EditText)v.findViewById(R.id.picture_tags);
        mTags.setText(mNote.getTags());
        mTags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mNote.setTags(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }
    public static PictureNoteFragment newInstance(String date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PICTURE_DATE, date);

        PictureNoteFragment fragment = new PictureNoteFragment();
        fragment.setArguments(args);

        return fragment;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (resultCode == getActivity().RESULT_OK && requestCode == TAKE_PICTURE_REQUEST){
//
//            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            File file = new File(Environment.getExternalStorageDirectory(), filename);
//            imageFileUri = Uri.fromFile(file);
//            mediaScanIntent.setData(imageFileUri);
//            mNote.setUriString(imageFileUri.toString());
//            getActivity().sendBroadcast(mediaScanIntent);
//
//        }
//    }

    @SuppressWarnings("deprecation")
    Bitmap scaleBitmap(Uri imageUri){
        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int imageViewHeight = display.getHeight();
        int imageViewWidth = display.getWidth();

        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inJustDecodeBounds = true;


        String photoFilePath = imageUri.getPath();

        BitmapFactory.decodeFile(photoFilePath, bOptions);

        int pictureHeight = bOptions.outHeight;
        int pictureWidth = bOptions.outWidth;

        int scaleFactor = Math.min(pictureHeight / imageViewHeight, pictureWidth / imageViewWidth);

        bOptions.inJustDecodeBounds = false;
        bOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath, bOptions);
        return bitmap;
    }
        }
