package com.kdudek.itemsapp.config.h2;

import com.kdudek.itemsapp.entity.Category;
import com.kdudek.itemsapp.entity.Item;
import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.entity.book.Author;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.entity.book.Publisher;
import com.kdudek.itemsapp.repository.AuthorRepository;
import com.kdudek.itemsapp.repository.BookRepository;
import com.kdudek.itemsapp.repository.CategoryRepository;
import com.kdudek.itemsapp.repository.ItemRepository;
import com.kdudek.itemsapp.repository.PublisherRepository;
import com.kdudek.itemsapp.repository.StorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.List;
import java.util.Set;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final StorageRepository storageRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        Category programming = Category.builder().name("Programming").build();
        Category tools = Category.builder().name("Tools").build();
        Category microcontrollers = Category.builder().name("Microcontrollers").build();
        categoryRepository.saveAll(List.of(
                programming,
                tools,
                microcontrollers
        ));

        Storage shelfA1 = Storage.builder().name("SHELF-A1").note("Main bookshelf, programming section").build();
        Storage shelfB1 = Storage.builder().name("SHELF-B1").note("Algorithms reference").build();
        Storage workbench = Storage.builder().name("WORKBENCH-1").note("Electronics bench left").build();
        Storage toolCabinet = Storage.builder().name("TOOL-CABINET-A").note("Measurement tools").build();
        Storage makerStorage = Storage.builder().name("MAKER-STORAGE-3").note("Filament and consumables").build();
        shelfA1.setParent(toolCabinet);
        shelfB1.setParent(toolCabinet);
        storageRepository.saveAll(List.of(
                shelfA1,
                shelfB1,
                workbench,
                toolCabinet,
                makerStorage
        ));

        Publisher prenticeHall = Publisher.builder().name("Prentice Hall").build();
        Publisher addisonWesley = Publisher.builder().name("Addison-Wesley").build();
        Publisher mitPress = Publisher.builder().name("MIT Press").build();
        publisherRepository.saveAll(List.of(
                prenticeHall,
                addisonWesley,
                mitPress
        ));

        Author robertMartin = Author.builder().name("Robert").surname("Martin").build();
        Author andrewHunt = Author.builder().name("Andrew").surname("Hunt").build();
        Author davidThomas = Author.builder().name("David").surname("Thomas").build();
        Author erichGamma = Author.builder().name("Erich").surname("Gamma").build();
        Author richardHelm = Author.builder().name("Richard").surname("Helm").build();
        authorRepository.saveAll(List.of(
                robertMartin,
                andrewHunt,
                davidThomas,
                erichGamma,
                richardHelm
        ));

        bookRepository.saveAll(List.of(
                Book.builder()
                        .title("Clean Code: A Handbook of Agile Software Craftsmanship")
                        .yearOfPublication(Year.of(2008))
                        .coverType("HARDCOVER")
                        .serialNumber("978-0-13-235088-4")
                        .editionNumber(1)
                        .catalogNumber("ENG-BOOK-001")
                        .note("Core reading for maintainable code")
                        .authors(Set.of(robertMartin))
                        .categories(Set.of(programming))
                        .publisher(prenticeHall)
                        .storage(shelfA1)
                        .build(),
                Book.builder()
                        .title("The Pragmatic Programmer")
                        .yearOfPublication(Year.of(1999))
                        .coverType("PAPERBACK")
                        .serialNumber("978-0-201-61622-4")
                        .editionNumber(2)
                        .catalogNumber("ENG-BOOK-002")
                        .note("20th Anniversary Edition")
                        .authors(Set.of(andrewHunt, davidThomas))
                        .categories(Set.of(programming))
                        .publisher(addisonWesley)
                        .storage(shelfA1)
                        .build(),
                Book.builder()
                        .title("Design Patterns: Elements of Reusable Object-Oriented Software")
                        .yearOfPublication(Year.of(1994))
                        .coverType("HARDCOVER")
                        .serialNumber("978-0-201-63361-0")
                        .editionNumber(1)
                        .catalogNumber("ENG-BOOK-003")
                        .note("Gang of Four classic")
                        .authors(Set.of(erichGamma, richardHelm))
                        .categories(Set.of(programming))
                        .publisher(addisonWesley)
                        .storage(shelfB1)
                        .build(),
                Book.builder()
                        .title("Introduction to Algorithms")
                        .yearOfPublication(Year.of(2009))
                        .coverType("HARDCOVER")
                        .serialNumber("978-0-262-03384-8")
                        .editionNumber(3)
                        .catalogNumber("ENG-BOOK-004")
                        .note("CLRS - reference for algorithms")
                        .authors(Set.of(robertMartin))
                        .categories(Set.of(programming))
                        .publisher(mitPress)
                        .storage(shelfB1)
                        .build(),
                Book.builder()
                        .title("The Art of Electronics")
                        .yearOfPublication(Year.of(2015))
                        .coverType("HARDCOVER")
                        .serialNumber("978-0-521-80926-9")
                        .editionNumber(3)
                        .catalogNumber("ENG-BOOK-005")
                        .note("Bible for makers and hardware engineers")
                        .authors(null)
                        .categories(Set.of(tools))
                        .publisher(null)
                        .storage(null)
                        .build()
        ));

        itemRepository.saveAll(List.of(
                Item.builder()
                        .description("Arduino Uno R3 Development Board")
                        .note("ATmega328P, original, with USB cable")
                        .categories(Set.of(microcontrollers))
                        .storage(workbench)
                        .build(),
                Item.builder()
                        .description("Raspberry Pi 5 8GB Starter Kit")
                        .note("Includes case, fan, and 64GB SD card")
                        .categories(null)
                        .storage(workbench)
                        .build(),
                Item.builder()
                        .description("Digital Multimeter with Auto-Ranging")
                        .note("Fluke 115, True RMS")
                        .categories(Set.of(tools))
                        .storage(toolCabinet)
                        .build(),
                Item.builder()
                        .description("Soldering Station 60W with Temperature Control")
                        .note("Hakko FX-888D, spare tips included")
                        .categories(Set.of(tools))
                        .storage(workbench)
                        .build(),
                Item.builder()
                        .description("3D Printer Filament PLA 1.75mm - Matte Black")
                        .note("1kg spool, Prusament")
                        .categories(null)
                        .storage(null)
                        .build()
        ));
    }
}
