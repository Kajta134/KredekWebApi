package pl.itembase.demo.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.itembase.demo.exception.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private final ItemDTOMapper itemDTOMapper;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemDTOMapper itemDTOMapper, ItemRepository itemRepository) {
        this.itemDTOMapper = itemDTOMapper;
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> getThings() {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemRepository.findAll()) {
            itemDTOS.add(itemDTOMapper.apply(item));
        }
        return itemDTOS;

    }

    public List<ItemDTO> getThingsFromRoom(Integer roomId) {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemRepository.findAll()) {
            if (item.getRoom().getId().equals(roomId)) {
                itemDTOS.add(itemDTOMapper.apply(item));
            }
        }
        return itemDTOS;
    }

    public ItemDTO getThing(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Thing with id " + id + " not found"));

        return itemDTOMapper.apply(item);
    }

    public ItemDTO createThing(Item item) {
        Item save = itemRepository.save(item);
        return itemDTOMapper.apply(save);
    }

    public ItemDTO updateThing(Long id, Item item) {
        return itemRepository.findById(id)
                .map(t -> {
                    item.setName(item.getName());
                    item.setRoom(item.getRoom());
                    return itemDTOMapper.apply(itemRepository.save(item));
                }).orElseThrow(() -> new ItemNotFoundException("Thing with id " + id + " not found"));
    }

    public ItemDTO patchThing(Long id, Item item) {
        return itemRepository.findById(id)
                .map(t -> {
                    if (item.getName() != null) item.setName(item.getName());
                    if (item.getRoom() != null) item.setRoom(item.getRoom());
                    return itemDTOMapper.apply(itemRepository.save(item));
                }).orElseThrow(() -> new ItemNotFoundException("Thing with id " + id + " not found"));
    }

    public void deleteThing(Long id) {
        if (itemRepository.findById(id).isPresent()) {
            itemRepository.deleteById(id);
        }else{
            throw new ItemNotFoundException("Thing with id " + id + " not found");
        }
    }
}
