package pl.itembase.demo.thing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/thing")
public class ThingController {

    private final ThingService thingService;

    @Autowired
    public ThingController(ThingService thingService) {
        this.thingService = thingService;
    }

    @GetMapping
    public ResponseEntity<List<ThingDTO>> getThing() {
        return ResponseEntity.ok(thingService.getThings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThingDTO> getThingById(@PathVariable Long id) {
        return ResponseEntity.ok(thingService.getThing(id));
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<ThingDTO>> getThingByRoomId(@PathVariable Integer id) {
        List<ThingDTO> thingsFromRoom = thingService.getThingsFromRoom(id);
        return ResponseEntity.ok(thingsFromRoom);
    }

    @PostMapping()
    public ResponseEntity<ThingDTO> createThing(@RequestBody Thing thing) {
        System.out.println("X");
        ThingDTO thing1 = thingService.createThing(thing);
        System.out.println("Y");
        URI location = getUri(thing);
        return ResponseEntity.created(location).body(thing1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThingDTO> updateThing(@PathVariable Long id,@RequestBody Thing thing) {
        return ResponseEntity.created(getUri(thing)).body(thingService.updateThing(id,thing));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ThingDTO> patchThing(@PathVariable Long id, @RequestBody Thing thing) {
        return ResponseEntity.created(getUri(thing)).body(thingService.patchThing(id,thing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThing(@PathVariable Long id) {
        thingService.deleteThing(id);
        return ResponseEntity.noContent().build();
    }

    private static URI getUri(Thing thing) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(thing.getId())
                .toUri();
    }




}
