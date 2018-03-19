package entities;

public class User {
    private String name;
    private String surName;
    private String patronymic;

    public User(String name, String surName, String patronymic) {
        this.name = name;
        this.surName = surName;
        this.patronymic = patronymic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + name.hashCode();
        result = 31 * result + surName.hashCode();
        result = 31 * result + patronymic.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        User user;

        if (obj == this){
            return true;
        }

        if (!(obj instanceof User)){
            return false;
        }

        user = (User)obj;

        return this.getName().equals(user.getName()) && this.getSurName().equals(user.getSurName()) && this.getPatronymic().equals(user.getPatronymic());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" : [\n");
        stringBuilder.append("  SurName:" + this.getSurName() + "\n");
        stringBuilder.append("  Name:" + this.getName() + "\n");
        stringBuilder.append("  Patronymic:" + this.getPatronymic() + "\n");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}