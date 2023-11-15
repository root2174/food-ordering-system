package com.food.ordering.system.order.service.dataaccess.order.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_address")
public class OrderAddressEntity {
	@Id
	private UUID id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private OrderEntity order;

	private String street;
	private String postalCode;
	private String city;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OrderAddressEntity that)) return false;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
