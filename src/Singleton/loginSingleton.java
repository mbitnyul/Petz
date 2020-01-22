package Singleton;

public class loginSingleton {
    
    private static loginSingleton instance = new loginSingleton();
    public static loginSingleton getInstance(){
        return instance;
    }
    
    private String username, password, nama;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    
    
}
