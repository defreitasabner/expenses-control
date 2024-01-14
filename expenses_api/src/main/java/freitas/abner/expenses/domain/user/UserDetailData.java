package freitas.abner.expenses.domain.user;

public record UserDetailData(
        Long id,
        String username,
        String email
) {
    public UserDetailData(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
