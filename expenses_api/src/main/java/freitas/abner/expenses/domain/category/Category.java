package freitas.abner.expenses.domain.category;

import freitas.abner.expenses.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Table(name = "Categories")
@Entity(name = "Category")
@EqualsAndHashCode(of = "id")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Category() {}

    public Category(CreateCategoryData categoryData, User user) {
        this.name = categoryData.name();
        this.user = user;
    };

    public Category(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }


    public Long getId() { return id; }

    public String getName() { return name; }

    public User getUser() {
        return user;
    }
}
