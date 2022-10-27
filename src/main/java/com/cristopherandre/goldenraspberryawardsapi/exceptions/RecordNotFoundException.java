package com.cristopherandre.goldenraspberryawardsapi.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(Long id) {
        super("Record not found: " + id);
    }

    public RecordNotFoundException(String name) {
        super("Record not found: " + name);
    }

}
