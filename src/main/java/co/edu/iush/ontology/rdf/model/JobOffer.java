package co.edu.iush.ontology.rdf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "job_offers")
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 255, message = "El nombre debe estar entre 6 y 255 carácteres")
    @Column(name = "occupation_name", nullable = false, length = 255)
    private String occupationName;

    @Size(min = 6, max = 255, message = "El nombre debe estar entre 6 y 255 carácteres")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Min(value = 1, message="Indique el salario")
    @Column(name = "salary", nullable = false)
    private int salary;

    @NotNull(message = "Debe indicar si es remoto")
    @Column(name = "remote")
    private Boolean remote;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOccupationName() {
        return occupationName;
    }

    public void setOccupationName(String occupationName) {
        this.occupationName = occupationName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
