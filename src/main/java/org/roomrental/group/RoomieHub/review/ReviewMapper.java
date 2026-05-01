package org.roomrental.group.RoomieHub.review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(source = "room.roomId", target = "roomId")
    @Mapping(source = "renter.userId", target = "renterId")
    @Mapping(source = "booking.bookingId", target = "bookingId")
    ReviewResponseDTO toDTO(Review review);

    Review toEntity(ReviewRequestDTO reviewRequestDTO);
}