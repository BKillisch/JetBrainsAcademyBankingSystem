class User {
    private String firstName;
    private String lastName;

    public User() {
        this.firstName = "";
        this.lastName = "";
    }

    public void setFirstName(String firstName) {
        this.firstName = java.util.Objects.requireNonNullElse(firstName, "");
    }

    public void setLastName(String lastName) {
        this.lastName = java.util.Objects.requireNonNullElse(lastName, "");
    }

    public String getFullName() {
        String fullName = (firstName + " " + lastName).trim();

        if (fullName.isEmpty()) {
            fullName = "Unknown";
        }

        return fullName;
    }
}