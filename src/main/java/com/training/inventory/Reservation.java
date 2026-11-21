package com.training.inventory;
import java.time.Instant;
public record Reservation(Long id,String warehouse,String sku,int quantity,String reference,String status,Instant createdAt) {}
