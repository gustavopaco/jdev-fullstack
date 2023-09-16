package com.pacoprojects.chart;

import com.pacoprojects.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final UsuarioRepository usuarioRepository;

    public List<ChartDto> getChartData() {
        try {
            List<ChartDto2> consultaUsandoDtoRecord = usuarioRepository.findUsuariosAndSalariosChart2ConsultaUsandoDtoRecord();
            List<ChartDto3> consultaUsandoDtoClass = usuarioRepository.findUsuariosAndSalariosChart3ConsultaUsandoDtoClass();
            List<UsuarioDtoInfo> dtoInterfaceMapStruct = usuarioRepository.findUsuariosAndSalariosChartConsultaUsandoDtoInterfaceMapStruct();
            List<UsuarioDtoInfo> dtoInterfaceMapStruct2 = usuarioRepository.findByNomeNotNullAndSalarioNotNull();

            return usuarioRepository.findUsuariosAndSalariosChartConsultaUsandoDtoInterface();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
