package pl.itembase.demo.thing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.itembase.demo.exception.ThingNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThingService {

    private final ThingDTOMapper thingDTOMapper;
    private final ThingRepository thingRepository;

    @Autowired
    public ThingService(ThingDTOMapper thingDTOMapper, ThingRepository thingRepository) {
        this.thingDTOMapper = thingDTOMapper;
        this.thingRepository = thingRepository;
    }

    public List<ThingDTO> getThings() {
        List<ThingDTO> thingDTOS = new ArrayList<>();
        for (Thing thing : thingRepository.findAll()) {
            thingDTOS.add(thingDTOMapper.apply(thing));
        }
        return thingDTOS;

    }

    public List<ThingDTO> getThingsFromRoom(Integer roomId) {
        List<ThingDTO> thingDTOS = new ArrayList<>();
        for (Thing thing : thingRepository.findAll()) {
            if (thing.getRoom().getId().equals(roomId)) {
                thingDTOS.add(thingDTOMapper.apply(thing));
            }
        }
        return thingDTOS;
    }

    public ThingDTO getThing(Long id) {
        Thing thing = thingRepository.findById(id)
                .orElseThrow(() -> new ThingNotFoundException("Thing with id " + id + " not found"));

        return thingDTOMapper.apply(thing);
    }

    public ThingDTO createThing(Thing thing) {
        Thing save = thingRepository.save(thing);
        return thingDTOMapper.apply(save);
    }

    public ThingDTO updateThing(Long id, Thing thing) {
        return thingRepository.findById(id)
                .map(t -> {
                    thing.setName(thing.getName());
                    thing.setRoom(thing.getRoom());
                    return thingDTOMapper.apply(thingRepository.save(thing));
                }).orElseThrow(() -> new ThingNotFoundException("Thing with id " + id + " not found"));
    }

    public ThingDTO patchThing(Long id, Thing thing) {
        return thingRepository.findById(id)
                .map(t -> {
                    if (thing.getName() != null) thing.setName(thing.getName());
                    if (thing.getRoom() != null) thing.setRoom(thing.getRoom());
                    return thingDTOMapper.apply(thingRepository.save(thing));
                }).orElseThrow(() -> new ThingNotFoundException("Thing with id " + id + " not found"));
    }

    public void deleteThing(Long id) {
        if (thingRepository.findById(id).isPresent()) {
            thingRepository.deleteById(id);
        }else{
            throw new ThingNotFoundException("Thing with id " + id + " not found");
        }
    }
}
