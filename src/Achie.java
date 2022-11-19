import USSM.USM.USM;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Achie {
    long date;
    String object;
    String  type;
    String photo;
    String measure;
    long count;
    Achie(long date, String object, String type, String photo, String measure, long count) {
        this.date = date;
        this.object = object;
        this.type = type;
        this.measure = measure;
        this.photo = photo;
        this.count = count;
    }
    public String toString() {
        Date achieDate = new Date(date);
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        return (dateFormat.format(achieDate).toString() + " \t " + object);
    }
    public String getObject() {
        return object;
    }
    public String getType() {
        return type;
    }
    public String getPhoto() {
        return photo;
    }
    public String getMeasure() {
        return measure;
    }
    public long getDate() {
        return date;
    }
    public long getCount() {
        return count;
    }
    static Achie[] getAchies(USM profile) {
        if (profile.opened()) {
            int size = profile.geti("date").size();
            Achie[] achies = new Achie[size];
            for (int index = 0; index < size; ++index) {
                achies[index] = new Achie(profile.geti("date").get(index), profile.gets("object").get(index), profile.gets("type").get(index), profile.gets("photo").get(index), profile.gets("measure").get(index), profile.geti("count").get(index));
            }
            return achies;
        } else {
            profile.create_isec("date");
            profile.create_ssec("object");
            profile.create_ssec("type");
            profile.create_ssec("photo");
            profile.create_ssec("measure");
            profile.create_isec("count");
            return new Achie[0];
        }
    }
}
