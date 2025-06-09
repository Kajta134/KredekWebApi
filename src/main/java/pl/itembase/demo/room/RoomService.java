package pl.itembase.demo.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.itembase.demo.exception.RoomNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final RoomDTOMapper roomDTOMapper ;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomDTOMapper roomDTOMapper, RoomRepository roomRepository) {
        this.roomDTOMapper = roomDTOMapper;
        this.roomRepository = roomRepository;
    }


    public List<RoomDTO> getAllRooms() {
        List<RoomDTO> rooms = new ArrayList<>();
        for (Room room : roomRepository.findAll()) {
            rooms.add(roomDTOMapper.apply(room));
        }
        return rooms;

    }

    public RoomDTO getRoomById(Integer id) {
        return roomDTOMapper.apply(roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("room with id " + id + " not found")));
    }

    public List<RoomDTO> getAllRoomsFromUser(Long userId) {
        List<RoomDTO> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> {
            if (room.getUser().getId().equals(userId)) {
                rooms.add(roomDTOMapper.apply(room));
            }
        });
        return rooms;
    }

    public RoomDTO createRoom(Room room) {
        return roomDTOMapper.apply(roomRepository.save(room));
    }

    public RoomDTO updateRoom(Integer id, Room room) {
        return roomRepository.findById(id).map(r -> {
                    r.setName(room.getName());
                    return roomDTOMapper.apply(roomRepository.save(r));
                })
                .orElseThrow(() -> new RoomNotFoundException("room with id " + id + " not found"));
    }

    public RoomDTO patchRoom(Integer id, Room room) {
        return roomRepository.findById(id).map(r -> {
                    if (room.getName() != null) r.setName(room.getName());
                    return roomDTOMapper.apply(roomRepository.save(r));
                })
                .orElseThrow(() -> new RoomNotFoundException("room with id " + id + " not found"));
    }

    public void deleteRoom(Integer id) {
        if (roomRepository.findById(id).isPresent()) {
            roomRepository.deleteById(id);
        }else{
            throw new RoomNotFoundException("room with id " + id + " not found");
        }


    }

}
