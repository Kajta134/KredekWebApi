package pl.itembase.demo.thing;

import org.springframework.stereotype.Service;
import pl.itembase.demo.room.Room;
import pl.itembase.demo.room.RoomDTO;
import pl.itembase.demo.room.RoomDTOMapper;

import java.util.function.Function;

@Service
public class ThingDTOMapper implements Function<Thing, ThingDTO> {
    RoomDTOMapper roomDTOMapper = new RoomDTOMapper();
    @Override
    public ThingDTO apply(Thing thing) {

       if(thing==null) return null;
        return new ThingDTO(
                thing.getId(),
                thing.getName(),
                roomDTOMapper.apply(thing.getRoom())
        );
    }
}
