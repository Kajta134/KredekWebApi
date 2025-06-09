package pl.itembase.demo.item;

import org.springframework.stereotype.Service;
import pl.itembase.demo.room.RoomDTOMapper;

import java.util.function.Function;

@Service
public class ItemDTOMapper implements Function<Item, ItemDTO> {
    RoomDTOMapper roomDTOMapper = new RoomDTOMapper();
    @Override
    public ItemDTO apply(Item item) {

       if(item ==null) return null;
        return new ItemDTO(
                item.getId(),
                item.getName(),
                roomDTOMapper.apply(item.getRoom())
        );
    }
}
