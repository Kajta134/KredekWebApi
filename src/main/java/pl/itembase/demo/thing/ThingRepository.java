package pl.itembase.demo.thing;

import org.springframework.data.repository.CrudRepository;

public interface ThingRepository extends CrudRepository<Thing, Long> {
}
