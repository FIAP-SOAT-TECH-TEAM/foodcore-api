package com.soat.fiap.food.core.api.user.mapper;

import com.soat.fiap.food.core.api.shared.mapper.AuditInfoMapper;
import com.soat.fiap.food.core.api.shared.mapper.RoleTypeMapper;
import com.soat.fiap.food.core.api.user.domain.model.Role;
import com.soat.fiap.food.core.api.user.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.request.UserRequest;
import com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.response.UserResponse;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper que converte entre DTOs e entidades de domínio para usuários
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RoleTypeMapper.class, AuditInfoMapper.class}
)
public interface UserDtoMapper {
    
    /**
     * Converte entidade de domínio para DTO de resposta
     * @param user Entidade de domínio
     * @return DTO de resposta
     */
    @Mapping(source = "role.id", target = "role")
    @Mapping(source = "auditInfo", target = "createdAt", qualifiedByName = "mapCreatedAt")
    @Mapping(source = "auditInfo", target = "updatedAt", qualifiedByName = "mapUpdatedAt")
    UserResponse toResponse(User user);
    
    /**
     * Converte lista de entidades de domínio para lista de DTOs de resposta
     * @param users Lista de entidades de domínio
     * @return Lista de DTOs de resposta
     */
    List<UserResponse> toResponseList(List<User> users);
    
    /**
     * Converte DTO de requisição para entidade de domínio
     * @param request DTO de requisição
     * @return Entidade de domínio
     */
    @Mapping(target = "id", ignore = true)
    User toDomain(UserRequest request);

    @AfterMapping
    default void customizeUserFromRequest(UserRequest request, @MappingTarget User user) {
        boolean emptyUser = (request.getEmail() == null || request.getEmail().isBlank()) &&
                (request.getDocument() == null || request.getDocument().isBlank()) &&
                (request.getUsername() == null || request.getUsername().isBlank());

        if (emptyUser || request.isGuest()) {
            user.markAsGuest();

            String guestName = "guest-" + java.util.UUID.randomUUID();

            // Preencher username obrigatório para não violar NOT NULL no BD
            if (user.getUsername() == null || user.getUsername().isBlank()) {
                user.setUsername(guestName);
            }

            // Se precisar, preencha outros campos obrigatórios também
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                user.setEmail(guestName + "@foodcore.local");
            }

            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(guestName);
            }
        } else if (request.getRole() != null) {
            Role role = new Role();
            role.setId((long) request.getRole().getId());
            role.setName(request.getRole().name());
            user.setRole(role);
        }
    }
    
    /**
     * Atualiza uma entidade de domínio com os dados de um DTO de requisição
     * @param request DTO de requisição com os dados atualizados
     * @param user Entidade de domínio a ser atualizada
     */
    @Mapping(target = "id", ignore = true)
    void updateDomainFromRequest(UserRequest request, @MappingTarget User user);
} 