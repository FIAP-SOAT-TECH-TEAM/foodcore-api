package unit.fixtures;

import com.soat.fiap.food.core.order.core.interfaceadapters.dto.payment.PaymentStatusDTO;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.payment.StatusDTO;

/**
 * Fixture para criação de objetos relacionados a pagamentos simulados
 * utilizados em testes unitários.
 */
public class PaymentFixture {

	/**
	 * Cria um pagamento válido com status aprovado.
	 *
	 * @param orderId
	 *            ID do pedido associado ao pagamento
	 * @return {@link PaymentStatusDTO} com status {@link StatusDTO#APPROVED}
	 */
	public static PaymentStatusDTO createApprovedPaymentStatus(Long orderId) {
		return new PaymentStatusDTO(orderId, StatusDTO.APPROVED);
	}

	/**
	 * Cria um pagamento com status pendente (ainda não aprovado).
	 *
	 * @param orderId
	 *            ID do pedido associado ao pagamento
	 * @return {@link PaymentStatusDTO} com status {@link StatusDTO#PENDING}
	 */
	public static PaymentStatusDTO createPendingPaymentStatus(Long orderId) {
		return new PaymentStatusDTO(orderId, StatusDTO.PENDING);
	}
}
