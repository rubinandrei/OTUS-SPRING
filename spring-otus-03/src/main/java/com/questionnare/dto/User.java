package com.questionnare.dto;

public final class User {

    private final String firstName;
    private final String lastName;
    private int countCorrectAnswers;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!firstName.equals(user.firstName)) return false;
        return lastName.equals(user.lastName);
    }

    @Override
    public int hashCode() {

        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    public int getCountCorrectAnswers() {
        return countCorrectAnswers;
    }

    public void setCountCorrectAnswers(int countCorrectAnswers) {
        this.countCorrectAnswers = countCorrectAnswers;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
