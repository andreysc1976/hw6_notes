package ru.a_party.hw6_notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Note implements Parcelable {

    private static ArrayList<Note> notes;

    private String uuid;
    private String name;
    private Date date;
    private String body;

    public Note(String name, Date date, String body) {
        this(UUID.randomUUID().toString(), name, date, body);
    }

    public static void setNotes(ArrayList<Note> notes) {
        Note.notes = notes;
    }

    public Note(){

    }

    public Note(String uuid, String name, Date date, String body) {
        this.uuid = uuid;
        this.name = name;
        this.date = date;
        this.body = body;
    }

    protected Note(Parcel in) {
        name = in.readString();
        body = in.readString();
        uuid = in.readString();
        try {
            date = MyDateUtil.stringToDate(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public static void updateNoteById(String uuid, String name, Date date, String body)
    {
        boolean finded = false;
        for (Note note:notes) {
            if (note.getUuid().equals(uuid)){
                note.setDate(date);
                note.setName(name);
                note.setBody(body);
                finded=true;
            }
        }
        if (!finded){
            notes.add(new Note(uuid,name,date,body));
        }
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.saveNote(new Note(uuid,name,date,body));
    }

    public static void deleteByIndex(int position) {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.removeNote(notes.get(position));
        notes.remove(position);
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public static ArrayList<Note> getNotes() {
        return notes;
    }

    public String getUuid() {
        return uuid;
    }

    public static void generateDemoNotes(){

        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date1 = MyDateUtil.stringToDate("01072021");
            date2 = MyDateUtil.stringToDate("02072021");
            date3 = MyDateUtil.stringToDate("07072021");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        notes = new ArrayList<>();
        notes.add(new Note("Первая",date1,"Моя большая первая заметка о создание приложений для андроид \n пробуем создавать заметки"));
        notes.add(new Note("Вторая",date1,"Тяжело в учение, легко в бою"));
        notes.add(new Note("где то в центре",date2,"Отмечаем серединную лекцию!"));
        notes.add(new Note("движемся к финишу",date2,"Осталось сдать где то около 10 контрольных и счасть наступит!!!"));
        notes.add(new Note("Ура!!!",date3,"Вот нам и выдали дипломы"));

        FirebaseHelper firebaseHelper = new FirebaseHelper();
        for (Note note:notes)
        {
            firebaseHelper.saveNote(note);
        }
    }

    public String getFormatedDate() {
        String result;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        result = simpleDateFormat.format(date);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(body);
        dest.writeString(uuid);
        dest.writeString(MyDateUtil.dateToString(date));
    }
}
