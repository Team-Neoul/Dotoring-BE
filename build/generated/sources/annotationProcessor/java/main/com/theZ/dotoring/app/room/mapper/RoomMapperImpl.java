package com.theZ.dotoring.app.room.mapper;

import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.dto.RoomResponseDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-02T18:32:07+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomResponseDTO toDTOFromMento(Room room, Mento receiver) {
        if ( room == null && receiver == null ) {
            return null;
        }

        RoomResponseDTO.RoomResponseDTOBuilder roomResponseDTO = RoomResponseDTO.builder();

        if ( room != null ) {
            roomResponseDTO.roomPK( room.getId() );
            roomResponseDTO.lastLetter( RoomMapper.letterListToStr( room.getLetterList() ) );
            roomResponseDTO.updateAt( room.getLastSendTime() );
        }
        if ( receiver != null ) {
            roomResponseDTO.nickname( receiver.getNickname() );
            roomResponseDTO.memberPK( receiver.getMentoId() );
            roomResponseDTO.major( RoomMapper.initMentoMajor( receiver.getMemberMajors() ) );
        }

        return roomResponseDTO.build();
    }

    @Override
    public RoomResponseDTO toDTOFromMenti(Room room, Menti receiver) {
        if ( room == null && receiver == null ) {
            return null;
        }

        RoomResponseDTO.RoomResponseDTOBuilder roomResponseDTO = RoomResponseDTO.builder();

        if ( room != null ) {
            roomResponseDTO.roomPK( room.getId() );
            roomResponseDTO.lastLetter( RoomMapper.letterListToStr( room.getLetterList() ) );
            roomResponseDTO.updateAt( room.getLastSendTime() );
        }
        if ( receiver != null ) {
            roomResponseDTO.nickname( receiver.getNickname() );
            roomResponseDTO.memberPK( receiver.getMentiId() );
            roomResponseDTO.major( RoomMapper.initMentiMajor( receiver.getMemberMajors() ) );
        }

        return roomResponseDTO.build();
    }
}
