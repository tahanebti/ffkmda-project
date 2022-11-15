package com.tahanebti.ffkmda.utils;


public class EnumUtil {

    static EnumDto enumToEnumDTO(Enum<?> e) {
        if ( e == null ) {
            return null;
        }
        EnumDto enumDTO = new EnumDto( e.name() );
        return enumDTO;
    }

    // approach two: enum -> String -> EnumDTO
    static String enumToString(Enum<?> e) {
        return e.name();
    }

    static EnumDto stringToEumDTO(String name) {
        return new EnumDto( name );
    }
    
}

