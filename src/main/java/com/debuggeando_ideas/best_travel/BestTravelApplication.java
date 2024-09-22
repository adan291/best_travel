package com.debuggeando_ideas.best_travel;

import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TourEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner {

	private final CustomerRepository customerRepository;
	private final FlyRepository flyRepository;
	private final HotelRepository hotelRepository;
	private final ReservationRepository reservationRepository;
	private final TicketRepository ticketRepository;
	private final TourRepository tourRepository;

	//Esto es lo que haria la anotacion @Autowired si lo pusiera encima de cada uno de los atributos de la clase
    public BestTravelApplication(CustomerRepository customerRepository, FlyRepository flyRepository,
								 HotelRepository hotelRepository, ReservationRepository reservationRepository,
								 TicketRepository ticketRepository, TourRepository tourRepository) {

        this.customerRepository = customerRepository;
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.tourRepository = tourRepository;
    }


    public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

    @Override
	public void run(String... args) throws Exception {

		/*var customer = customerRepository.findById("VIKI771012HMCRG093").isPresent();
		var fly = flyRepository.findById(15L).isPresent();
		var hotel = hotelRepository.findById(7L).isPresent();
		var reservation = reservationRepository.findById(UUID.fromString("32345678-1234-5678-1234-567812345678")).isPresent();
		var ticket = ticketRepository.findById(UUID.fromString("32345678-1234-5678-4234-567812345678")).isPresent();
		var tour = tourRepository.findById(7L).isPresent();

		log.info(String.valueOf(customer));
		log.info(String.valueOf(fly));
		log.info(String.valueOf(hotel));
		log.info(String.valueOf(reservation));
		log.info(String.valueOf(ticket));
		log.info(String.valueOf(tour));

		System.out.println("#####################################NATIVE#################################################");
		this.flyRepository.selectLessPriceNative(BigDecimal.valueOf(20)).forEach(System.out::println);

		System.out.println("#####################################JPQL####################################################");

		System.out.println("-------------------------------------Less Price-------------------------------------");
		this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(System.out::println);

		System.out.println("-------------------------------------Between Price-------------------------------------");
		this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10), BigDecimal.valueOf(15)).forEach(System.out::println);
		
		System.out.println("-------------------------------------Origin Destiny-------------------------------------");
		this.flyRepository.selectOriginDestiny("Grecia", "Mexico").forEach(System.out::println);*/

		var customer = customerRepository.findById("GOTW771012HMRGR087").orElseThrow();
        log.info("Client name {}", customer.getFullName());

		var fly = flyRepository.findById(11L).orElseThrow();
		log.info("Fly name " + fly.getDestinyName());

		var hotel = hotelRepository.findById(3L).orElseThrow();
		log.info("Hotel name " + hotel.getName());

		var tour = TourEntity.builder()
				.customer(customer)
				.build();

		var ticket = TicketEntity.builder()
				.id(UUID.randomUUID())
				.price(fly.getPrice().multiply(BigDecimal.TEN))
				.arrivalDate(LocalDate.now())
				.departureDate(LocalDate.now())
				.purchaseDate(LocalDate.now())
				.customer(customer)
				.tour(tour)
				.fly(fly)
				.build();

		var reservation = ReservationEntity.builder()
				.id(UUID.randomUUID())
				.dateTimeReservation(LocalDateTime.now())
				.dateStart(LocalDate.now())
				.dateEnd(LocalDate.now())
				.hotel(hotel)
				.customer(customer)
				.tour(tour)
				.totalDays(1)
				.price(fly.getPrice().multiply(BigDecimal.TEN))
				.build();

	}
}
