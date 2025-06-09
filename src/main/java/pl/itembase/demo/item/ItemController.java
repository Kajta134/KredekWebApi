package pl.itembase.demo.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/thing")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getThing() {
        return ResponseEntity.ok(itemService.getThings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getThingById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getThing(id));
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<ItemDTO>> getThingByRoomId(@PathVariable Integer id) {
        List<ItemDTO> thingsFromRoom = itemService.getThingsFromRoom(id);
        return ResponseEntity.ok(thingsFromRoom);
    }

    @PostMapping()
    public ResponseEntity<ItemDTO> createThing(@RequestBody Item item) {
        System.out.println("X");
        ItemDTO thing1 = itemService.createThing(item);
        System.out.println("Y");
        URI location = getUri(item);
        return ResponseEntity.created(location).body(thing1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateThing(@PathVariable Long id, @RequestBody Item item) {
        return ResponseEntity.created(getUri(item)).body(itemService.updateThing(id, item));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDTO> patchThing(@PathVariable Long id, @RequestBody Item item) {
        return ResponseEntity.created(getUri(item)).body(itemService.patchThing(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThing(@PathVariable Long id) {
        itemService.deleteThing(id);
        return ResponseEntity.noContent().build();
    }

    private static URI getUri(Item item) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.getId())
                .toUri();
    }




}
