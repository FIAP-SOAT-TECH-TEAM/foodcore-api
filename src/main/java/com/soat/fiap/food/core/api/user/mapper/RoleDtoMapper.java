package com.soat.fiap.food.core.api.user.mapper;

import com.soat.fiap.food.core.api.shared.mapper.AuditInfoMapper;
import com.soat.fiap.food.core.api.user.domain.model.Role;
import com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.request.RoleRequest;
import com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre DTOs e entidades de domínio para roles
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = AuditInfoMapper.class
)
public interface RoleDtoMapper {

    /**
     * Converte entidade de domínio para DTO de resposta
     * @param role Entidade de domínio
     * @return DTO de resposta
     */
    RoleResponse toResponse(Role role);

    /**
     * Converte lista de entidades de domínio para lista de DTOs de resposta
     * @param roles Lista de entidades de domínio
     * @return Lista de DTOs de resposta
     */
    List<RoleResponse> toResponseList(List<Role> roles);

    /**
     * Converte DTO de requisição para entidade de domínio
     * @param request DTO de requisição
     * @return Entidade de domínio
     */
    @Mapping(target = "id", ignore = true)
    Role toDomain(RoleRequest request);

    /**
     * Atualiza uma entidade de domínio com os dados de um DTO de requisição
     * @param request DTO de requisição com os dados atualizados
     * @param role Entidade de domínio a ser atualizada
     */
    @Mapping(target = "id", ignore = true)
    void updateDomainFromRequest(RoleRequest request, @MappingTarget Role role);
}