package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto;

import java.util.Arrays;
import java.util.Objects;

/**
 * DTO reoresentando dados básicos para upload de imagem. Inclui o nome do
 * arquivo e seu conteúdo.
 */
public record FileUploadDTO(String fileName, byte[] content) {

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof FileUploadDTO(String name, byte[] content1)))
			return false;
		return Objects.equals(fileName, name) && Arrays.equals(content, content1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, Arrays.hashCode(content));
	}

	@Override
	public String toString() {
		return "FileUploadDTO{" + "fileName=" + fileName + "\\" + ", content=" + Arrays.toString(content) + '}';
	}
}
