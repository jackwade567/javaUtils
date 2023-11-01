public interface ParticipantRepositoryCustom {
    List<Object[]> getCustomParticipantDto(String teamNameFilter, String roleNameFilter, String managerNameFilter,
                                           String languageFilter, String sortColumn, String sortDirection, 
                                           Integer pageSize, Integer pageNumber);
}

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ParticipantRepositoryImpl implements ParticipantRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${schema.name}")
    private String schemaName;

    @Override
    public List<Object[]> getCustomParticipantDto(String teamNameFilter, String roleNameFilter, String managerNameFilter,
                                                  String languageFilter, String sortColumn, String sortDirection, 
                                                  Integer pageSize, Integer pageNumber) {
        String queryString = String.format(
            "SELECT t1.cm_participant_id, t1.racf, t1.lob, t1.first_name, t1.last_name, " +
            "t2.team_name, t3.role_name, t4.manager_firstname, t4.manager_lastname, " +
            "STRING_AGG(t5.lang_name || '--' || t5.language_id , '<-->') " +
            "FROM %1$s.cm_participant t1 " +
            "JOIN %1$s.team t2 ON t1.team_id = t2.team_id " +
            "JOIN %1$s.role t3 ON t1.role_id = t3.role_id " +
            "JOIN %1$s.manager t4 ON t1.manager_id = t4.manager_id " +
            "LEFT JOIN %1$s.cm_participant_language p1 ON p1.cm_participant_id = t1.cm_participant_id " +
            "LEFT JOIN %1$s.language t5 ON t5.language_id = p1.language_id " +
            "WHERE (t2.team_name = ?1 OR ?1 IS NULL) " +
            "AND (t3.role_name = ?2 OR ?2 IS NULL) " +
            "AND (CONCAT(t4.manager_firstname, ' ', t4.manager_lastname) = ?3 OR ?3 IS NULL) " +
            "AND (t5.lang_name = ?4 OR ?4 IS NULL) " +
            "GROUP BY t1.cm_participant_id, t2.team_name, t3.role_name, t4.manager_firstname, t4.manager_lastname " +
            "ORDER BY CASE WHEN ?5 IS NULL OR ?5 = '' THEN t1.cm_participant_id END ASC, " +
            "CASE WHEN ?5 IS NOT NULL AND ?5 <> '' AND ?6 = 'DESC' THEN t1.cm_participant_id END DESC " +
            "LIMIT ?7 OFFSET ?8", schemaName);

        Query query = entityManager.createNativeQuery(queryString)
                                   .setParameter(1, teamNameFilter)
                                   .setParameter(2, roleNameFilter)
                                   .setParameter(3, managerNameFilter)
                                   .setParameter(4, languageFilter)
                                   .setParameter(5, sortColumn)
                                   .setParameter(6, sortDirection)
                                   .setParameter(7, pageSize)
                                   .setParameter(8, pageNumber);

        return query.getResultList();
    }
}

public interface ParticipantRepository extends JpaRepository<Participant, Long>, ParticipantRepositoryCustom {
    // ... existing methods ...
}
