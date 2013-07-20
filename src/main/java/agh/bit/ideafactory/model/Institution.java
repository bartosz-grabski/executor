package agh.bit.ideafactory.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author bgrabski
 * Class representing institution (business) account in Executor system
 */
@Entity
@Table(name="Institution")
public class Institution {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id")
    public Long id;

    @Column(name = "email")
    @Email
    @NotEmpty
    public String email;

    @Column(name = "password")
    @NotEmpty
    public String password;
}
