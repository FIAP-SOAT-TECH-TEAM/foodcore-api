package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AuthenticatedUserSource;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementação de {@link AuthenticatedUserSource} que extrai as informações do
 * usuário autenticado a partir dos headers HTTP da requisição atual.
 * <p>
 * Os headers são definidos no API Management, após a autenticação do usuário, e
 * propagados para o backend.
 * </p>
 */
@Component
public class DefaultAuthenticatedUserSource implements AuthenticatedUserSource {

	private HttpServletRequest getCurrentRequest() {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs instanceof ServletRequestAttributes servletAttrs) {
			return servletAttrs.getRequest();
		}

		throw new IllegalStateException("Nenhuma requisição HTTP ativa encontrada.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSubject() {
		return getCurrentRequest().getHeader("Auth-Subject");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return getCurrentRequest().getHeader("Auth-Name");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEmail() {
		return getCurrentRequest().getHeader("Auth-Email");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCpf() {
		return getCurrentRequest().getHeader("Auth-Cpf");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRole() {
		return getCurrentRequest().getHeader("Auth-Role");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalDateTime getCreationDate() {
		String createdAt = getCurrentRequest().getHeader("Auth-CreatedAt");
		if (createdAt == null) {
			return null;
		}
		return LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME);
	}
}
