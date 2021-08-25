package br.com.curso.webmvnspringbootmicroservicos.repository;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoLocalizationRepository extends JpaRepository<Usuario, Long> {
    String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(m.latitude)) * cos(radians(m.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.latitude))))";

    @Query(value = "select u from Usuario u where (6371 * acos(cos(radians(:latitude)) * cos(radians(u.latitude)) * cos(radians(u.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(u.latitude)))) < :distance order by (6371 * acos(cos(radians(:latitude)) * cos(radians(u.latitude)) * cos(radians(u.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(u.latitude)))) desc", nativeQuery = true)
    Page<Usuario> findEntitiesByLocation(@Param(value = "latitude") final double latitude,
                                         @Param(value = "longitude") final double longitude,
                                         @Param(value = "distance") final double distance,
                                         Pageable pageable);
}
