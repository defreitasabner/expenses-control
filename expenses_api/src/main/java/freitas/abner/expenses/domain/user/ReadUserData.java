package freitas.abner.expenses.domain.user;

public record ReadUserData(
        Long id,
        String username,
        String email
) {
    public ReadUserData(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
