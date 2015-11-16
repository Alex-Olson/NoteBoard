package com.example.angel.noteboard;

import android.app.ListFragment;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class NoteListFragment extends ListFragment {
    private final String TAG = "signefinaweoierngdf";
    private ArrayList<Note> mNotes;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mNotes = NoteManager.get(getActivity()).getNotes();

        NoteAdapter adapter = new NoteAdapter(mNotes);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l ,View v, int position, long id){
        TextNote n = (TextNote)(getListAdapter()).getItem(position);
        Log.d(TAG, n.getContent() + " was clicked");
    }

    private class NoteAdapter extends ArrayAdapter<Note>{
        public NoteAdapter(ArrayList<Note> notes){
            super(getActivity(), 0, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                if
                        (getItem(position) instanceof TextNote) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_text_note, null);
                    TextNote tn = (TextNote) getItem(position);
                    TextView contentTextView = (TextView)convertView.findViewById(R.id.note_list_text_item_content);

                    contentTextView.setText(tn.getContent());
                } else if (getItem(position) instanceof PictureNote){

                    //TODO; do picture note stuff
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_picture_note, null);
                    ImageView imageView = (ImageView)convertView.findViewById(R.id.note_list_picture_item);
                    imageView.setImageResource(R.mipmap.ic_launcher);

                }
            }
            return convertView;
        }
    }

}
