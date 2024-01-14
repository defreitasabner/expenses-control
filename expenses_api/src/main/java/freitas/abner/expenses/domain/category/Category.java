package freitas.abner.expenses.domain.category;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Table(name = "Categories")
@Entity(name = "Category")
@EqualsAndHashCode(of = "id")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Category() {};

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() { return id; }

    public String getName() { return name; }
}
