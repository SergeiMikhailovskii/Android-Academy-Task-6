package com.mikhailovskii.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mTitleEdit;
    private EditText mBodyEdit;
    private Button mAddButton;
    private RecyclerView mNotesRecycler;
    private List<Note> notes;
    private AppDatabase database;
    private NoteDao noteDao;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        database = App.getInstance().getDatabase();
        noteDao = database.noteDao();

        mTitleEdit = findViewById(R.id.title_edittext);
        mBodyEdit = findViewById(R.id.body_edittext);
        mAddButton = findViewById(R.id.add_button);
        mNotesRecycler = findViewById(R.id.notes_recycler);

        mNotesRecycler.setLayoutManager(new GridLayoutManager(this, 1));

        mAddButton.setOnClickListener(v->onAddClick());

        invalidateRecyclerView();

        Stetho.initializeWithDefaults(getApplicationContext());
    }

    private void onAddClick(){
        String title = mTitleEdit.getText().toString();
        String body = mBodyEdit.getText().toString();
        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        noteDao.insertNote(note);
        invalidateRecyclerView();
    }

    private void onItemClickListener(int position){
        noteDao.deleteNote(notes.get(position));
        invalidateRecyclerView();
    }

    private void invalidateRecyclerView(){
        notes = noteDao.getNotes();
        adapter = new RecyclerViewAdapter(notes, getApplicationContext());
        adapter.setOnItemClickListener((position, item) -> onItemClickListener(position));
        mNotesRecycler.setAdapter(adapter);
    }

}
