package com.training.inventory;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1")
public class InventoryController {
 private final InventoryService s; public InventoryController(InventoryService s){this.s=s;}
 @PutMapping("/stock/{warehouse}/{sku}") public Stock adjust(@PathVariable String warehouse,@PathVariable String sku,@RequestParam int quantity){return s.adjust(warehouse,sku,quantity);}
 @GetMapping("/stock/{warehouse}/{sku}") public Stock stock(@PathVariable String warehouse,@PathVariable String sku){return s.get(warehouse,sku);}
 @PostMapping("/reservations") @ResponseStatus(org.springframework.http.HttpStatus.CREATED) public Reservation reserve(@Valid @RequestBody ReservationRequest r){return s.reserve(r);}
 @PostMapping("/reservations/{id}/release") public Reservation release(@PathVariable long id){return s.release(id);}
 @GetMapping("/reservations/{id}") public Reservation reservation(@PathVariable long id){return s.getReservation(id);}
}
