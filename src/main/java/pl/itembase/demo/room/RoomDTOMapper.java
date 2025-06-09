package pl.itembase.demo.room;

import org.springframework.stereotype.Service;
import pl.itembase.demo.user.UserDTOMapper;

import java.util.function.Function;

@Service
public class RoomDTOMapper implements Function<Room, RoomDTO> {
     UserDTOMapper userDTOMapper = new UserDTOMapper();


    @Override
    public RoomDTO apply(Room room) {
        if(room == null) return null;
        return new RoomDTO(
                room.getId(),
                room.getName(),
                userDTOMapper.apply(room.getUser())
        );
    }
}
