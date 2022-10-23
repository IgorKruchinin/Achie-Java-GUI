import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
}
