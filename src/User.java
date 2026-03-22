public abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected String phoneNum;
    private static int id_number =0 ;

    public User(String name, String email, String phoneNum) {
        this.id = id_number;
        id_number+=1;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public abstract void dashboard();

    public void updateInfo(String newEmail, String newPhoneNum){
        this.email = newEmail;
        this.phoneNum=newPhoneNum;
        System.out.println("Information updated successfully.");
    }

    @Override
    public String toString()
    {
        return "User " + id + ", name:  " + name + ", email: " + email + ", phone number: " + phoneNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

        public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
