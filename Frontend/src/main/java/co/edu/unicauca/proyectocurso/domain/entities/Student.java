
package co.edu.unicauca.proyectocurso.domain.entities;

import java.util.UUID;

/**
 *
 * @author ibell
 */
public class Student extends User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String program;
    private String projectID;
   
    public Student(String email, String password, String firstName, String lastName, String program, String projectID) {
        super(email, password, "Estudiante");
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.projectID = projectID;
    }
    
    public Student(UUID id) {
        super("", "", "Estudiante"); // Se deja el email y password vacíos por defecto
        this.id = id;
    }
    
    public Student(String id, String firstName, String lastName, String program, String projectID){
        super("x", "x", "Estudiante");
        this.id = UUID.fromString(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.projectID = projectID;
        
 
    }

    public String getFirstName() {
        return firstName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
   
    @Override
    public String getRole() {
        return "Student";
    }
}
