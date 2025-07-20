package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto;

import java.util.Arrays;
import java.util.Objects;

public record FileUploadDTO(String fileName, byte[] content) {

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof FileUploadDTO other))
			return false;
		return Objects.equals(fileName, other.fileName) && Arrays.equals(content, other.content);
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
