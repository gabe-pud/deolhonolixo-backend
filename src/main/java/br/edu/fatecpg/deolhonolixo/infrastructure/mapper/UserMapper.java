package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.LoginCaseOutputDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserLoginRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UserLoginAndRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "role")
    UserJpaEntity toJpaFromDomain(User user);
    @Mapping(target = "role", source = "roles")
    User toDomainFromJpa(UserJpaEntity userJpa);
    User toDomainFromRegisterRequestDto(UserRegisterRequestDTO dto);
    User toDomainFromLoginRequestDTO(UserLoginRequestDTO dto);
    UserLoginAndRegisterResponseDTO toRegisterResponseDto(User user);
    UserLoginAndRegisterResponseDTO toLoginResponseDto(LoginCaseOutputDTO dto);

}
