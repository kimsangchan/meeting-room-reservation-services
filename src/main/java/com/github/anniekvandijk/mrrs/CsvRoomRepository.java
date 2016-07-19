package com.github.anniekvandijk.mrrs;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.ToIntFunction;

public class CsvRoomRepository extends RoomRepository {
    private static final int COL_NAME = 0;
    private static final int COL_LOCATION = 1;
    private static final int COL_CAPACITY = 2;
    private static final int COL_FACILITIES = 3;
    private static Logger logger = LoggerFactory.getLogger(CsvRoomRepository.class);


    public static RoomRepository create(Reader reader, char delimiter) throws IOException {

        CsvRoomRepository repo = new CsvRoomRepository();
            
        repo.read(reader, delimiter);
        return repo;
    }

    private void read(Reader reader, char delimiter) throws IOException {
        CSVReader csvReader = new CSVReader(reader, delimiter);

        csvReader.readNext(); // read the first row (header)
        String[] nextLine = null;
        while ((nextLine = csvReader.readNext()) != null) {
            Set<Facility> facilities = new TreeSet<>();
            String[] facilityList = (nextLine[COL_FACILITIES]).split(",");
            for (String facility : facilityList) {
                String facilityCln = StringUtils.trimToNull(facility);
                if (facilityCln == null) {
                    // do nothing
                } else {
                    facilities.add(new Facility(facilityCln));
                }
            }
            logger.debug(facilities.size() + " facilities added to meetingroom " + nextLine[COL_NAME]);
            add(new MeetingRoom(nextLine[COL_NAME], nextLine[COL_LOCATION], Integer.parseInt(nextLine[COL_CAPACITY]), facilities));
        }
        csvReader.close();

    }
}
