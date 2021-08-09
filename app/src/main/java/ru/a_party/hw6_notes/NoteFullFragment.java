package ru.a_party.hw6_notes;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFullFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFullFragment extends Fragment implements NoteListFragment.NoteUpdater {

    final Calendar myCalendar = Calendar.getInstance();

    private boolean isLandscape = false;

    private static final String ARG_UUID = "uuid";
    private static final String ARG_NAME = "name";
    private static final String ARG_DATE = "date";
    private static final String ARG_BODY = "body";

    private static final String ARG_NOTE = "note";

    private final SimpleDateFormat format = new SimpleDateFormat("dd.MMMM.yyyy");

    // TODO: Rename and change types of parameters
    private Note note;

    private TextView editTextNoteName;
    private TextView textViewNoteDate;
    private EditText editTextNoteBody;

    private NoteListFragment.NoteUpdater noteUpdater;

    public NoteFullFragment() {
        // Required empty public constructor
    }


    public static NoteFullFragment newInstance(Note note) {
        Bundle args = new Bundle();
        args.putString(ARG_UUID, note.getUuid());
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
            String uuid = getArguments().getString(ARG_UUID);
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
        editTextNoteName = view.findViewById(R.id.textViewNoteName);
        textViewNoteDate = view.findViewById(R.id.textViewNoteDate);
        editTextNoteBody = view.findViewById(R.id.textViewNoteBody);

        view.findViewById(R.id.textViewNoteDate).setOnClickListener(v -> new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        view.findViewById(R.id.textViewNoteName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(requireActivity());
                View editNameView = li.inflate(R.layout.edit_text, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setView(editNameView);

                EditText editName = editNameView.findViewById(R.id.editTextTextPersonName);

                builder.setCancelable(false)
                        .setTitle("Edit name")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editTextNoteName.setText(editName.getText().toString());
                                note.setName(editName.getText().toString());
                            }
                        })
                        .show();
            }
        });

        view.findViewById(R.id.buttonSave).setOnClickListener(v -> {
            Note.updateNoteById(note.getUuid(),editTextNoteName.getText().toString(),note.getDate(),editTextNoteBody.getText().toString());
            noteUpdater.update();
            if (!isLandscape)
            {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

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
        editTextNoteName.setText(note.getName());
        textViewNoteDate.setText(format.format(note.getDate()));
        editTextNoteBody.setText(note.getBody());
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
        updateText();
    }


    public void setUpdater(NoteListFragment.NoteUpdater noteUpdater) {
        this.noteUpdater = noteUpdater;
    }

    @Override
    public void update() {
        noteUpdater.update();
    }
}