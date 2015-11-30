package com.example.angel.noteboard;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;


public class NoteListFragment extends ListFragment {
    private final String TAG = "NoteListFragment";
    private ArrayList<Note> mNotes;
    Uri imageFileUri;
    private static final int TAKE_PICTURE_REQUEST = 0;

    final String filename = "temp_photo.jpg";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mNotes = NoteManager.get(getActivity()).getNotes();



        NoteAdapter adapter = new NoteAdapter(mNotes);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note_list, menu);
    }


    @Override
    public void onListItemClick(ListView l ,View v, int position, long id){
        Note n = (Note)(getListAdapter()).getItem(position);

        if (getListAdapter().getItem(position) instanceof TextNote) {
            Intent i = new Intent(getActivity(), NoteActivity.class);
            i.putExtra(NoteFragment.EXTRA_NOTE_DATE, n.getDate());
            startActivity(i);


        } else if (getListAdapter().getItem(position) instanceof PictureNote){

            Intent i = new Intent(getActivity(), PictureNoteActivity.class);
            i.putExtra(PictureNoteFragment.EXTRA_PICTURE_DATE, n.getDate());
            startActivity(i);

            }
        }


    private class NoteAdapter extends ArrayAdapter<Note>{
        public NoteAdapter(ArrayList<Note> notes){
            super(getActivity(), 0, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                if (getItem(position) instanceof TextNote) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_text_note, null);
                    TextNote tn = (TextNote) getItem(position);
                    TextView contentTextView = (TextView)convertView.findViewById(R.id.note_list_text_item_content);

                    contentTextView.setText(tn.getContent());
                } else if (getItem(position) instanceof PictureNote){

                    //TODO; do picture note stuff
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_picture_note, null);
                    ImageView imageView = (ImageView)convertView.findViewById(R.id.note_list_picture_item);
                    PictureNote pn = (PictureNote) getItem(position);
                    //Bitmap image = scaleBitmap(Uri.parse(pn.getUriString()));
                    //imageView.setImageBitmap(image);

                }
            }
            return convertView;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_item_new_text_note:
                TextNote tn = new TextNote();
                NoteManager.get(getActivity()).addNote(tn);
                Intent i = new Intent(getActivity(), NoteActivity.class);
                i.putExtra(NoteFragment.EXTRA_NOTE_DATE, tn.getDate());
                startActivityForResult(i, 0);
                return true;

            case R.id.menu_item_new_picture_note:

                PictureNote pn = new PictureNote();
                NoteManager.get(getActivity()).addNote(pn);
                Intent j = new Intent(getActivity(), PictureNoteActivity.class);
                j.putExtra(PictureNoteFragment.EXTRA_PICTURE_DATE, pn.getDate());
                startActivityForResult(j, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

