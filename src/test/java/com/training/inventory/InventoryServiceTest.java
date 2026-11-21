package com.training.inventory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class InventoryServiceTest {
 @Test void reservesAndReleasesStock(){var s=new InventoryService();s.adjust("WH1","SKU1",10);
  var r=s.reserve(new ReservationRequest("WH1","SKU1",4,"ORDER-1"));assertEquals(6,s.get("WH1","SKU1").available());
  s.release(r.id());assertEquals(10,s.get("WH1","SKU1").available());}
}
