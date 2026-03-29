package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UserLoginAndRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserJpaEntity toJpaFromDomain(User user);
    User toDomainFromJpa(UserJpaEntity userJpa);
    User toDomainFromRegisterRequestDto(UserRegisterRequestDTO dto);
    UserLoginAndRegisterResponseDTO toLoginRegisterResponseDto(User user);
}
