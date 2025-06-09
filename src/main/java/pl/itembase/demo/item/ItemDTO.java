package pl.itembase.demo.item;

import pl.itembase.demo.room.RoomDTO;

public record ItemDTO(Long id, String name, RoomDTO room) {
}
