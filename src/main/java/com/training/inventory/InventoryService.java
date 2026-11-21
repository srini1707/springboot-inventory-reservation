package com.training.inventory;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class InventoryService {
 private final Map<String,Stock> stocks=new ConcurrentHashMap<>();
 private final Map<Long,Reservation> reservations=new ConcurrentHashMap<>();
 private final AtomicLong ids=new AtomicLong();
 private String key(String w,String s){return w.toUpperCase()+":"+s.toUpperCase();}
 public synchronized Stock adjust(String w,String sku,int qty){if(qty<0)throw new IllegalArgumentException("quantity cannot be negative");
  var k=key(w,sku);var old=stocks.getOrDefault(k,new Stock(w,sku,0,0,0));
  if(qty<old.reserved())throw new IllegalStateException("on-hand quantity cannot be below reserved quantity");
  var n=new Stock(w,sku,qty,old.reserved(),old.version()+1);stocks.put(k,n);return n;}
 public Stock get(String w,String sku){var s=stocks.get(key(w,sku));if(s==null)throw new NoSuchElementException("Stock not found");return s;}
 public synchronized Reservation reserve(ReservationRequest r){var k=key(r.warehouse(),r.sku());var s=get(r.warehouse(),r.sku());
  if(s.available()<r.quantity())throw new IllegalStateException("Insufficient available stock");
  stocks.put(k,new Stock(s.warehouse(),s.sku(),s.onHand(),s.reserved()+r.quantity(),s.version()+1));
  var x=new Reservation(ids.incrementAndGet(),r.warehouse(),r.sku(),r.quantity(),r.reference(),"RESERVED",Instant.now());reservations.put(x.id(),x);return x;}
 public synchronized Reservation release(long id){var r=getReservation(id);if(!r.status().equals("RESERVED"))throw new IllegalStateException("Reservation is not active");
  var s=get(r.warehouse(),r.sku());stocks.put(key(r.warehouse(),r.sku()),new Stock(s.warehouse(),s.sku(),s.onHand(),s.reserved()-r.quantity(),s.version()+1));
  var n=new Reservation(r.id(),r.warehouse(),r.sku(),r.quantity(),r.reference(),"RELEASED",r.createdAt());reservations.put(id,n);return n;}
 public Reservation getReservation(long id){var r=reservations.get(id);if(r==null)throw new NoSuchElementException("Reservation not found");return r;}
}
