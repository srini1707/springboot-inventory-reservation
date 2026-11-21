package com.training.inventory;
import jakarta.validation.constraints.*;
public record ReservationRequest(@NotBlank String warehouse,@NotBlank String sku,@Min(1) int quantity,@NotBlank String reference) {}
