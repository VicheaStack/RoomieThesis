package org.roomrental.group.RoomieHub.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "room.roomId", target = "roomId")
    @Mapping(source = "renter.userId", target = "renterId")
    @Mapping(source = "payment.paymentId", target = "paymentId")
    @Mapping(source = "review.reviewId", target = "reviewId")
    @Mapping(target = "status", expression = "java(booking.getStatus() != null ? booking.getStatus().name() : null)")
    BookingResponseDTO toDTO(Booking booking);

    @Mapping(target = "bookingId", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "renter", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "review", ignore = true)
    @Mapping(target = "conversation", ignore = true)
    @Mapping(target = "totalNights", ignore = true)
    @Mapping(target = "pricePerNight", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "cancellationReason", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Booking toEntity(BookingRequestDTO dto);
}