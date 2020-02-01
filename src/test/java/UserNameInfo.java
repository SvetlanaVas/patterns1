import lombok.Value;

@Value
public class UserNameInfo {
    private final String surName;
    private final String name;

    public String toString() {
        return this.getSurName() + " " + this.getName();
    }
}
