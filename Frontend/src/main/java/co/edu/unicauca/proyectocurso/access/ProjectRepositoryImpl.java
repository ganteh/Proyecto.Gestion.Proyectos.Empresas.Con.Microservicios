package co.edu.unicauca.proyectocurso.access;

import co.edu.unicauca.proyectocurso.domain.entities.*;
import co.edu.unicauca.proyectocurso.domain.services.CompanyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ProjectRepositoryImpl implements IProjectRepository {
    
    private Connection connection;
     private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8081/projects"; // Cambia esto a la URL real

    public ProjectRepositoryImpl(Connection conn) {
        this.connection = conn;
    }
    
    public ProjectRepositoryImpl() {}

    @Override
    public boolean save(Project project, String nitEmpresa) {
        // Añadir el campo 'summary' en la consulta SQL
        String sql = "INSERT INTO projects (id, name, summary, description, date, state, company_nit, budget, max_months, objectives) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 10 parámetros

        try (Connection conn = DatabaseConnection.getNewConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, project.getId().toString());
            pstmt.setString(2, project.getName());
            pstmt.setString(3, project.getSummary()); // Nuevo campo
            pstmt.setString(4, project.getDescription());
            pstmt.setDate(5, Date.valueOf(project.getDate()));
            pstmt.setString(6, project.getState().toString());
            pstmt.setString(7, nitEmpresa);
            pstmt.setFloat(8, project.getBudget());
            pstmt.setInt(9, project.getMaxMonths());
            pstmt.setString(10, project.getObjectives());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar el proyecto: " + e.getMessage());
            return false;
        }
    }

     @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        try {
            // Crear solicitud GET
            HttpGet request = new HttpGet(baseUrl);
            request.setHeader("Accept", "application/json");

            // Ejecutar solicitud
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String json = EntityUtils.toString(response.getEntity());
                projects = mapper.readValue(json, new TypeReference<List<Project>>() {});
            } else {
                System.err.println("Error al obtener proyectos, código: " + statusCode);
            }
        } catch (Exception e) {
            System.err.println("Error al conectar con el microservicio de proyectos: " + e.getMessage());
        }
        return projects;
    }
    @Override
    public boolean update(Project project) {
        // Añadir el campo 'summary' en la consulta SQL
        String sql = "UPDATE projects SET name = ?, summary = ?, description = ?, date = ?, state = ?, company_nit = ?, budget = ?, max_months = ?, objectives = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getNewConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getSummary()); // Nuevo campo
            pstmt.setString(3, project.getDescription());
            pstmt.setDate(4, Date.valueOf(project.getDate()));
            pstmt.setString(5, project.getState().toString());
            pstmt.setString(6, project.getCompany() != null ? project.getCompany().getNit() : null);
            pstmt.setFloat(7, project.getBudget());
            pstmt.setInt(8, project.getMaxMonths());
            pstmt.setString(9, project.getObjectives());
            pstmt.setString(10, project.getId().toString()); // ID ahora es el parámetro 10

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el proyecto: " + e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param projectId
     * @return
     */
    @Override
    public boolean delete(UUID projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection conn = DatabaseConnection.getNewConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, projectId.toString());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el proyecto: " + e.getMessage());
            return false;
        }
    }
    @Override
    public List<Project> findProjectsByCompanyNIT(String nit) {
        List<Project> projects = new ArrayList<>();
        // Añadir el campo "summary" en la consulta SQL
        String sql = "SELECT id, name, summary, description, budget, max_months FROM projects WHERE company_nit = ?";

        try (Connection conn = DatabaseConnection.getNewConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setId(UUID.fromString(rs.getString("id")));
                project.setName(rs.getString("name"));
                project.setSummary(rs.getString("summary")); // Nuevo campo
                project.setDescription(rs.getString("description"));
                project.setBudget(rs.getFloat("budget"));
                project.setMaxMonths(rs.getInt("max_months"));

                projects.add(project);
            }

        } catch (SQLException e) {
            Logger.getLogger(ProjectRepositoryImpl.class.getName()).log(Level.SEVERE, "Error al obtener proyectos por NIT", e);
        }

        return projects;
    }

    @Override
    public Project findById(UUID projectId) {
        String sql = "SELECT id, name, summary, description, date, state, company_nit, budget, max_months, objectives FROM projects WHERE id = ?";

        try (Connection conn = DatabaseConnection.getNewConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projectId.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Project project = new Project();
                project.setId(UUID.fromString(rs.getString("id")));
                project.setName(rs.getString("name"));
                project.setSummary(rs.getString("summary")); // Nuevo campo
                project.setDescription(rs.getString("description"));
                project.setDate(rs.getDate("date").toLocalDate());
                project.setState(ProjectState.valueOf(rs.getString("state")));
                project.setBudget(rs.getFloat("budget"));
                project.setMaxMonths(rs.getInt("max_months"));
                project.setObjectives(rs.getString("objectives"));

                // Cargar la empresa asociada
                String companyNit = rs.getString("company_nit");
                if (companyNit != null) {
                    CompanyService companyService = new CompanyService(new CompanyRepositoryImpl());
                    Company company = companyService.findCompanyByNit(companyNit);
                    project.setCompany(company);
                    if (company != null) {
                        company.addProject(project); // Relación bidireccional
                    }
                }
                return project;
            }

        } catch (SQLException e) {
            Logger.getLogger(ProjectRepositoryImpl.class.getName()).log(Level.SEVERE, "Error al obtener el proyecto por ID", e);
        }

        return null; // Retorna null si no se encuentra el proyecto
    }
}