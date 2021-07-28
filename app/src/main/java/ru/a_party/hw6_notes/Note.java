package ru.a_party.hw6_notes;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Note {

    private static ArrayList<Note> notes;

    final private UUID uuid;
    private String name;
    private Date date;
    private String body;

    public Note(String name, Date date, String body) {
        this(UUID.randomUUID(), name, date, body);
    }

    public Note(UUID uuid, String name, Date date, String body) {
        this.uuid = uuid;
        this.name = name;
        this.date = date;
        this.body = body;
    }

    public static void updateNoteById(UUID uuid,String name,Date date,String body)
    {
        for (Note note:notes) {
            if (note.getUuid().equals(uuid)){
                note.setDate(date);
                note.setName(name);
                note.setBody(body);
            }
        }
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

    public UUID getUuid() {
        return uuid;
    }

    public static void clear(){
        notes=new ArrayList<>();
    }

    public static void generateDemoNotes(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date date1;
        Date date2;
        Date date3;
        try {
            calendar.setTime(dateFormat.parse("01072021"));
            date1 = calendar.getTime();
            calendar.setTime(dateFormat.parse("05072021"));
            date2 = calendar.getTime();
            calendar.setTime(dateFormat.parse("07072021"));
            date3 = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        notes = new ArrayList<>();
        notes.add(new Note("Первая",date1,"Моя большая первая заметка о создание приложений для андроид \n пробуем создавать заметки"));
        notes.add(new Note("Вторая",date1,"Тяжело в учение, легко в бою"));
        notes.add(new Note("где то в центре",date2,"Отмечаем серединную лекцию!"));
        notes.add(new Note("движемся к финишу",date2,"Осталось сдать где то около 10 контрольных и счасть наступит!!!"));
        notes.add(new Note("Ура!!!",date3,"Вот нам и выдали дипломы"));
    }
}
