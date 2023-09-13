package com.pacoprojects.service;

import com.pacoprojects.dto.TelefoneAddDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.mapper.TelefoneMapper;
import com.pacoprojects.model.Telefone;
import com.pacoprojects.repository.TelefoneRepository;
import com.pacoprojects.util.BeanValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final TelefoneMapper telefoneMapper;
    private final BeanValidator beanValidator;

    public TelefoneDto addTelefone(TelefoneAddDto telefoneAddDto, BindingResult bindingResult) {
        beanValidator.validate(bindingResult);
        Telefone telefone = telefoneMapper.toEntity(telefoneAddDto);
        return telefoneMapper.toDto1(telefoneRepository.save(telefone));
    }

    public void deleteTelefone(Long id) {
        telefoneRepository.deleteById(id);
    }
}
