package com.pacoprojects.repository;

import com.pacoprojects.chart.ChartDto;
import com.pacoprojects.chart.ChartDto2;
import com.pacoprojects.chart.ChartDto3;
import com.pacoprojects.chart.UsuarioDtoInfo;
import com.pacoprojects.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByUsername(String username);

    Page<Usuario> findAllByNomeContainsIgnoreCase(String nome, Pageable pageable);


    @Query(value = "SELECT u.nome as nome, u.salario as salario from usuario u where u.salario is not null and u.nome is not null ")
    List<ChartDto> findUsuariosAndSalariosChartConsultaUsandoDtoInterface();

    @Query(value = "SELECT new com.pacoprojects.chart.ChartDto2( u.nome, u.salario) from usuario u where u.salario is not null and u.nome is not null ")
    List<ChartDto2> findUsuariosAndSalariosChart2ConsultaUsandoDtoRecord();

    @Query(value = "SELECT new com.pacoprojects.chart.ChartDto3(u.nome, u.salario) FROM usuario u WHERE u.salario IS NOT NULL AND u.nome IS NOT NULL")
    List<ChartDto3> findUsuariosAndSalariosChart3ConsultaUsandoDtoClass();

    @Query(value = "select u.nome as nome, u.salario as salario from usuario u where u.salario is not null and u.nome is not null ")
    List<UsuarioDtoInfo> findUsuariosAndSalariosChartConsultaUsandoDtoInterfaceMapStruct();

    @Query("select u from usuario u where u.nome is not null and u.salario is not null")
    List<UsuarioDtoInfo> findByNomeNotNullAndSalarioNotNull();

//    @Query(value =
//            "select array_agg(nome) from usuario where salario IS NOT NULL " +
//            " UNION ALL " +
//            " select cast(array_agg(salario) as character varying[]) from usuario where salario IS NOT NULL"
//            , nativeQuery = true)
//    List<String> findUsuariosAndSalariosChart();
}
