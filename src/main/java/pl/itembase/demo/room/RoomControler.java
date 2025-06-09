package pl.itembase.demo.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.itembase.demo.user.UserDTO;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomControler {

    private final RoomService roomService;

    @Autowired
    public RoomControler(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public ResponseEntity<List<RoomDTO>> room() {
        List<RoomDTO> rooms = roomService.getAllRooms();

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> room(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<RoomDTO>> user(@PathVariable Long id) {
        List<RoomDTO> allRoomsFromUser = roomService.getAllRoomsFromUser(id);

        return ResponseEntity.ok(allRoomsFromUser);
    }

    @PostMapping()
    public ResponseEntity<RoomDTO> createRoom(@RequestBody Room room) {
        RoomDTO roomDTO = roomService.createRoom(room);

        URI location = getUri(room);

        return ResponseEntity.created(location).body(roomDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Integer id,@RequestBody Room room) {
        RoomDTO roomDTO = roomService.updateRoom(id, room);

        URI location = getUri(room);

        return ResponseEntity.created(location).body(roomDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoomDTO> patchRoom(@PathVariable Integer id, @RequestBody Room room) {
        RoomDTO roomDTO = roomService.patchRoom(id, room);

        URI location = getUri(room);

        return ResponseEntity.created(location).body(roomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer id) {
        deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    private static URI getUri(Room room) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(room.getId())
                .toUri();
    }
}
