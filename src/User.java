class User {
    private String username;
    private String password;
    private Wallet wallet;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + wallet.toString();
    }

    public static User fromString(String data) {
        String[] parts = data.split(",", 3);
        User user = new User(parts[0], parts[1]);
        user.wallet = Wallet.fromString(parts[2]);
        return user;
    }
}