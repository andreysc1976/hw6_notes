package ru.a_party.hw6_notes;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFullFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFullFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();

    private static final String ARG_UUID = "uuid";
    private static final String ARG_NAME = "name";
    private static final String ARG_DATE = "date";
    private static final String ARG_BODY = "body";

    private static final String ARG_NOTE = "note";

    private final SimpleDateFormat format = new SimpleDateFormat("dd.MMMM.yyyy");

    // TODO: Rename and change types of parameters
    private Note note;

    private TextView textViewNoteName;
    private TextView textViewNoteDate;
    private TextView textViewNoteBody;

    public NoteFullFragment() {
        // Required empty public constructor
    }


    public static NoteFullFragment newInstance(Note note) {
        Bundle args = new Bundle();
        args.putString(ARG_UUID, note.getUuid().toString());
        args.putString(ARG_NAME, note.getName());
        args.putSerializable(ARG_DATE, note.getDate());
        NoteFullFragment fragment = new NoteFullFragment();
        args.putString(ARG_BODY, note.getBody());
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UUID uuid = UUID.fromString(getArguments().getString(ARG_UUID));
            String name = getArguments().getString(ARG_NAME);
            Date date = (Date) getArguments().getSerializable(ARG_DATE);

            myCalendar.setTime(date);

            String body = getArguments().getString(ARG_BODY);
            note = new Note(uuid,name,date,body);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_note_full, container, false);
        textViewNoteName = view.findViewById(R.id.textViewNoteName);
        textViewNoteDate = view.findViewById(R.id.textViewNoteDate);
        textViewNoteBody = view.findViewById(R.id.textViewNoteBody);

        view.findViewById(R.id.textViewNoteDate).setOnClickListener(v -> new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        return view;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            note.setDate(myCalendar.getTime());
            Note.updateNoteById(note.getUuid(),note.getName(),note.getDate(),note.getBody());
            updateText();
        }

    };

    private void updateText(){
        textViewNoteName.setText(note.getName());
        textViewNoteDate.setText(format.format(note.getDate()));
        textViewNoteBody.setText(note.getBody());
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateText();
    }
}