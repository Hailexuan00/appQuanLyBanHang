package fpoly.hailxph49396.duan1_quanlybanhang.DTO;

public class NhanVienDTO {
    private String username;
    private String password;
    private String name;
    private String middleName;
    private String gender;
    private String phone;
    private String email;
    private String address;

    public NhanVienDTO() {
    }

    public NhanVienDTO(String username, String password, String name, String middleName, String gender, String phone, String email, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.middleName = middleName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
