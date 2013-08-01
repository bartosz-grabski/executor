package agh.bit.ideafactory.model;


import javax.persistence.*;

@Entity
@Table(name="Limits")
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="limit_id")
    private Long id;

    @Column(name="memory_limit")
    private Long memoryLimit;

    @Column(name="time_limit")
    private Long timeLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public Long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Long timeLimit) {
        this.timeLimit = timeLimit;
    }
}
