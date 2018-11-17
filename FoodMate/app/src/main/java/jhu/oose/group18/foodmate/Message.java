package jhu.oose.group18.foodmate;

public class Message {
    public String display1;
    public int pic;
    public int year;
    public String name;
    public String category;

    public Message() {

    }
    public Message(String display1, String display2, int pic) {
        this.display1 = display1;
        this.pic = pic;
        this.year = pic;
    }

    public String getDisplay1() {
        return display1;
    }

    public void setDisplay1(String display1) {
        this.display1 = display1;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
