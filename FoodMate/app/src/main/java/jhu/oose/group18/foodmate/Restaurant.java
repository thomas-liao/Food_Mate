package jhu.oose.group18.foodmate;

public class Restaurant {
    public String title;
    public int pic;
    public int year;
    public String name;
    public String category;

    public Restaurant() {

    }

    public Restaurant(String title, int pic, int year) {
        this.title = title;
        this.pic = pic;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
