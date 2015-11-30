package com.example.angel.noteboard;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class NoteFragment extends Fragment {
    private TextNote mNote;
    private EditText mContent;
    private TextView mDateDisplay;

    public static final String EXTRA_NOTE_DATE = "com.example.angel.noteboard.note_date";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String date = getArguments().getSerializable(EXTRA_NOTE_DATE).toString();

        mNote = (TextNote) NoteManager.get(getActivity()).getNote(date);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_note, parent, false);
        mDateDisplay = (TextView)v.findViewById(R.id.note_date);
        mDateDisplay.setText(mNote.getDate().toString());

        mContent = (EditText)v.findViewById(R.id.note_content);
        mContent.setText(mNote.getContent());

        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mNote.setContent(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }

    public static NoteFragment newInstance(String date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NOTE_DATE, date);

        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
