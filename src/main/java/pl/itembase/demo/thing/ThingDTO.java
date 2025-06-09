package pl.itembase.demo.thing;

import pl.itembase.demo.room.RoomDTO;

public record ThingDTO(Long id, String name, RoomDTO room) {
}
