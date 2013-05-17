package agh.bit.ideafactory.model;

import javax.persistence.*;

@Entity
@Table(name = "Example")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "example_id")
    private Long id;

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Example(String name){
        this.setName(name);
    }
}
